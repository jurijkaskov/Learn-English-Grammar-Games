package com.learnenglish.grammargames.data.model.book

import kotlinx.serialization.Serializable

@Serializable
data class BookMappingMetadataDto(
    val id: String,
    val title: String,
    val author: String,
    val edition: String,
    val editionId: String,
    val publicationYear: Int = 2019,
    val totalUnits: Int
)

@Serializable
data class BookCoverageDto(
    val totalUnits: Int,
    val mappedUnits: Int,
    val unmappedUnits: Int = 0,
    val coveragePercentage: Float = 100.0f
)

@Serializable
data class BookUnitDto(
    val unit: Int,
    val unitTitle: String,
    val bookSection: String,
    val mappedTopicId: String,
    val mappedTopicTitle: String? = null,
    val mappedSectionId: String? = null,
    val status: String = "MAPPED"
)

@Serializable
data class BookMappingFileDto(
    val book: BookMappingMetadataDto,
    val targetCourseId: String,
    val targetCourseLevel: String,
    val cefrRange: String,
    val coverage: BookCoverageDto? = null,
    val units: List<BookUnitDto> = emptyList()
)
