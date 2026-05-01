package com.an.habittracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.an.habittracker.model.Habit
import com.an.habittracker.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListViewModel(application: Application): AndroidViewModel(application) {
    val habitsLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val habitsLD = MutableLiveData<ArrayList<Habit>>()

    private val type = object : TypeToken<ArrayList<Habit>>() {}.type
    private val fileHelper = FileHelper(getApplication())

    init {
        loadHabits()
    }

    fun loadHabits() {
        loadingLD.value = true
        habitsLoadErrorLD.value = false

        try {
            val json = fileHelper.readFromFile()
            if (json.isNotEmpty()) {
                val habits = Gson().fromJson<ArrayList<Habit>>(json, type)
                habitsLD.value = habits
            } else {
                habitsLD.value = arrayListOf()
            }
        } catch (e: Exception) {
            habitsLoadErrorLD.value = true
            habitsLD.value = arrayListOf()
        } finally {
            loadingLD.value = false
        }
    }

    fun addHabit(habit: Habit) {
        val currentList = habitsLD.value ?: arrayListOf()
        currentList.add(habit)
        habitsLD.value = currentList
        saveHabits(currentList)
    }

    fun updateHabitProgress(habitName: String, newProgress: Int) {
        val currentList = habitsLD.value ?: return
        val index = currentList.indexOfFirst { it.name == habitName }
        if (index != -1) {
            val oldHabit = currentList[index]
            val updatedHabit = oldHabit.copy(progress = newProgress)
            currentList[index] = updatedHabit
            habitsLD.value = currentList
            saveHabits(currentList)
        }
    }

    private fun saveHabits(list: ArrayList<Habit>) {
        val json = Gson().toJson(list)
        fileHelper.writeToFile(json)
    }
}