package io.github.emmiii.randommealplanner.model

data class DayMeal(
    val day: String,
    val meal: String? = null,
    val isPinned: Boolean = false
)
