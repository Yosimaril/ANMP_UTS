package com.an.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.an.habittracker.model.Habit

class DetailViewModel: ViewModel() {
    val habitLD = MutableLiveData<Habit>()

    fun fetch() {
        val habitDummy1 = Habit("1")
        habitLD.value = habitDummy1
    }
}