package com.learnenglish.grammargames.feature.onboarding.presentation.complete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.domain.usecase.CompleteOnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingCompleteViewModel @Inject constructor(
    private val completeOnboardingUseCase: CompleteOnboardingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingCompleteUiState())
    val uiState: StateFlow<OnboardingCompleteUiState> = _uiState.asStateFlow()

    fun onAction(action: OnboardingCompleteUiAction, onFinished: () -> Unit) {
        when (action) {
            OnboardingCompleteUiAction.FinishOnboarding -> {
                _uiState.update { it.copy(isCompleting = true) }
                viewModelScope.launch {
                    completeOnboardingUseCase()
                    onFinished()
                }
            }
        }
    }
}
