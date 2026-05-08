package com.an.habittracker.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.an.habittracker.databinding.HabitListItemBinding
import com.an.habittracker.model.Habit
import com.an.habittracker.viewmodel.HabitViewModel

class HabitListAdapter(
    val habitList: ArrayList<Habit>,
    val viewModel: HabitViewModel
) : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {
    class HabitViewHolder(var binding: HabitListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]

        with(holder.binding) {
            txtHabitTitle.text = habit.name
            txtHabitDescription.text = habit.description
            progressBar.max = habit.goal
            progressBar.progress = habit.progress
            txtProgress.text = "${habit.progress} / ${habit.goal} ${habit.unit}"
            imgHabit.setImageResource(habit.iconResId)

            if (habit.progress >= habit.goal) {
                prog.text = "Completed"
                textView4.text = "Completed"
                textView4.setBackgroundColor(Color.parseColor("#2E8B57"))
            } else {
                prog.text = "Progress"
                textView4.text = "In Progress"
                textView4.setBackgroundColor(Color.parseColor("#075A96"))
            }

            btnHabitPlus.setOnClickListener {
                if (habit.progress < habit.goal) {
                    viewModel.updateHabitProgress(habit.id, habit.progress + 1)
                }
            }

            btnHabitMinus.setOnClickListener {
                if (habit.progress > 0) {
                    viewModel.updateHabitProgress(habit.id, habit.progress - 1)
                }
            }
        }
    }

    override fun getItemCount(): Int = habitList.size

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }
}