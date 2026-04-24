package com.an.habittracker.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.an.habittracker.databinding.HabitListItemBinding
import com.an.habittracker.model.Habit

class HabitListAdapter(val habitList:ArrayList<Habit>) :RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {
    class HabitViewHolder(var binding: HabitListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]

        with (holder.binding) {
            txtHabitTitle.text = habit.name
            txtHabitDescription.text = habit.description

            progressBar.max = habit.goal
            progressBar.progress = habit.progress

            txtProgress.text = "${habit.progress} / ${habit.goal} ${habit.unit}"
            imgHabit.setImageResource(habit.iconResId)
        }
    }

    override fun getItemCount(): Int {
        return habitList.size
    }


    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }
}
