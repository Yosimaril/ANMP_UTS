package com.an.habittracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun loadHabits() {
        loadingLD.value = true
        habitsLoadErrorLD.value = false

        try {
            val json = fileHelper.readFromFile()

            if (json.isNotEmpty()) {
                val habits = Gson().fromJson<ArrayList<Habit>>(json, type)
                habitsLD.value = habits
                Log.d("LOG_INFO", "Parsed habits size: ${habits.size}")
            } else {
                habitsLD.value = arrayListOf()
                Log.d("LOG_INFO", "File is empty, returning empty list")
            }
        } catch (e: Exception) {
            habitsLoadErrorLD.value = true
            habitsLD.value = arrayListOf()
            Log.e("LOG_INFO", "Error loading habits", e)
        }

        loadingLD.value = false
    }

    fun addHabit(habit: Habit) {
        val currentList = habitsLD.value ?: arrayListOf()
        currentList.add(habit)
        habitsLD.value = currentList
        saveHabits(currentList)
        Log.d("LOG_INFO", "Added new habit")
    }

    private fun saveHabits(list: ArrayList<Habit>) {
        val json = Gson().toJson(list)
        fileHelper.writeToFile(json)
        Log.d("LOG_INFO", "New habits saved")
    }
}