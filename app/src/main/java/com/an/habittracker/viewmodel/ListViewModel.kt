package com.an.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.an.habittracker.model.Habit

class ListViewModel: ViewModel() {
    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val habitsLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    fun refresh() {
        loadingLD.value = true
        habitsLoadErrorLD.value = false
        habitsLD.value = arrayListOf(
            Habit("1"),
            Habit("2"),
            Habit("3")
        )

        habitsLoadErrorLD.value = false
        loadingLD.value = false
    }

}