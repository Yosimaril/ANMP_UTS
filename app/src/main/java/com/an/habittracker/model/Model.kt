package com.an.habittracker.model

data class Habit(
    val name: String,
    val description: String,
    val goal: Int,
    val progress: Int = 0,
    val unit: String,
    val iconResId: Int
)