package com.learnenglish.grammargames.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.domain.repository.UserPreferencesRepository
import com.learnenglish.grammargames.domain.usecase.AddUserXpUseCase
import com.learnenglish.grammargames.domain.usecase.ObserveCoursesUseCase
import com.learnenglish.grammargames.domain.usecase.ObserveUserPreferencesUseCase
import com.learnenglish.grammargames.domain.usecase.ObserveUserProgressUseCase
import com.learnenglish.grammargames.domain.usecase.UpdateDailyGoalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    observeCoursesUseCase: ObserveCoursesUseCase,
    observeUserProgressUseCase: ObserveUserProgressUseCase,
    observeUserPreferencesUseCase: ObserveUserPreferencesUseCase,
    private val addUserXpUseCase: AddUserXpUseCase,
    private val updateDailyGoalUseCase: UpdateDailyGoalUseCase,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        observeCoursesUseCase(),
        observeUserProgressUseCase(),
        observeUserPreferencesUseCase()
    ) { courses, progress, preferences ->
        HomeUiState(
            isLoading = false,
            courses = courses,
            progress = progress,
            preferences = preferences
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState(isLoading = true)
    )

    fun onAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.SelectCourse -> {
                viewModelScope.launch {
                    userPreferencesRepository.setSelectedCourseId(action.courseId)
                }
            }
            is HomeUiAction.AddXp -> {
                viewModelScope.launch {
                    addUserXpUseCase(action.amount)
                }
            }
            is HomeUiAction.UpdateDailyGoal -> {
                viewModelScope.launch {
                    updateDailyGoalUseCase(action.minutes)
                }
            }
        }
    }
}
