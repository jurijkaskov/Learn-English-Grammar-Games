package com.learnenglish.grammargames.domain.validator

import com.learnenglish.grammargames.domain.model.book.BookCompanionEditionMapping
import com.learnenglish.grammargames.domain.model.book.BookMappingValidationReport
import com.learnenglish.grammargames.domain.model.book.BookUnitItem

object BookMappingValidator {

    fun validate(
        mapping: BookCompanionEditionMapping,
        validTopicIds: Set<String>? = null
    ): BookMappingValidationReport {
        val errors = mutableListOf<String>()
        val warnings = mutableListOf<String>()

        val expectedUnits = (1..mapping.totalUnits).toSet()
        val unitNumbers = mapping.units.map { it.unitNumber }
        val foundUnitsSet = unitNumbers.toSet()

        // 1. Missing units check
        val missingUnits = expectedUnits - foundUnitsSet
        if (missingUnits.isNotEmpty()) {
            errors.add(
                "Missing ${missingUnits.size} unit(s) in ${mapping.bookTitle} (${mapping.editionId}): ${missingUnits.sorted().take(10)}${if (missingUnits.size > 10) "..." else ""}"
            )
        }

        // 2. Duplicate units check
        val duplicates = unitNumbers.groupBy { it }
            .filter { it.value.size > 1 }
            .keys
        if (duplicates.isNotEmpty()) {
            errors.add(
                "Duplicate unit number(s) detected in ${mapping.editionId}: ${duplicates.sorted()}"
            )
        }

        // 3. Out-of-bounds units
        val outOfBounds = foundUnitsSet.filter { it < 1 || it > mapping.totalUnits }
        if (outOfBounds.isNotEmpty()) {
            errors.add(
                "Unit numbers outside 1..${mapping.totalUnits} detected in ${mapping.editionId}: $outOfBounds"
            )
        }

        // 4. Topic ID existence check (if catalog provided)
        if (validTopicIds != null) {
            for (unit in mapping.units) {
                if (unit.mappedTopicId.isBlank()) {
                    errors.add("Unit ${unit.unitNumber} has blank mappedTopicId")
                } else if (!validTopicIds.contains(unit.mappedTopicId)) {
                    errors.add(
                        "Unit ${unit.unitNumber} maps to non-existent topicId '${unit.mappedTopicId}'"
                    )
                }
            }
        }

        // 5. Copyright boundary check
        // Ensure unit titles are concise TOC headings and not full exercise texts or answers
        for (unit in mapping.units) {
            if (unit.unitTitle.isBlank()) {
                errors.add("Unit ${unit.unitNumber} has empty title")
            }
            if (unit.unitTitle.length > 250) {
                warnings.add(
                    "Unit ${unit.unitNumber} title is unusually long (${unit.unitTitle.length} chars), verify copyright boundary"
                )
            }
        }

        val mappedCount = mapping.units.count { it.status == "MAPPED" && it.unitNumber in expectedUnits }
        val coveragePercentage = if (mapping.totalUnits > 0) {
            (mappedCount.toFloat() / mapping.totalUnits) * 100.0f
        } else {
            0.0f
        }

        val isValid = errors.isEmpty() && missingUnits.isEmpty() && duplicates.isEmpty() && mappedCount == mapping.totalUnits

        return BookMappingValidationReport(
            bookId = mapping.bookId,
            editionId = mapping.editionId,
            isValid = isValid,
            errors = errors,
            warnings = warnings,
            totalUnits = mapping.totalUnits,
            mappedUnitsCount = mappedCount,
            missingUnits = missingUnits,
            duplicateUnits = duplicates,
            coveragePercentage = coveragePercentage
        )
    }
}
