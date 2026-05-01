package com.an.habittracker.model

import java.util.UUID

data class Habit(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val goal: Int,
    val progress: Int = 0,
    val unit: String,
    val iconResId: Int
)