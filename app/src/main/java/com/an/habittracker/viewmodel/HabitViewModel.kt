package com.an.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.an.habittracker.model.Habit
import com.an.habittracker.model.HabitFormState
import com.an.habittracker.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HabitViewModel(application: Application): AndroidViewModel(application) {
    val habitsLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val formStateLD = MutableLiveData<HabitFormState>()

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

    fun validateHabit(name: String, description: String, goalString: String, unit: String, iconResId: Int) {
        var nameError: String? = null
        var descriptionError: String? = null
        var goalError: String? = null
        var unitError: String? = null

        val goal = goalString.toIntOrNull()
        if (name.isEmpty()) nameError = "Name required"
        if (description.isEmpty()) descriptionError = "Description required"
        if (goal == null || goal <= 0) goalError = "Invalid goal"
        if (unit.isEmpty()) unitError = "Unit required"
        val isValid = nameError == null && descriptionError == null && goalError == null && unitError == null
        formStateLD.value = HabitFormState(nameError, descriptionError, goalError, unitError, isValid)

        if (isValid) {
            val habit = Habit(
                name = name,
                description = description,
                goal = goal!!,
                progress = 0,
                unit = unit,
                iconResId = iconResId
            )
            addHabit(habit)
        }
    }

    fun addHabit(habit: Habit) {
        val currentList = habitsLD.value ?: arrayListOf()
        currentList.add(habit)
        habitsLD.value = currentList
        saveHabits(currentList)
    }

    fun resetFormState() {
        formStateLD.value = HabitFormState()
    }

    fun updateHabitProgress(habitId: String, newProgress: Int) {
        val currentList = habitsLD.value ?: return
        val index = currentList.indexOfFirst { it.id == habitId }
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