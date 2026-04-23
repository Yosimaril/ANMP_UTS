package com.an.habittracker.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.an.habittracker.databinding.HabitListItemBinding
import com.an.habittracker.model.Habit

class HabitListAdapter(val habitList:ArrayList<Habit>)
    :RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.binding.txtHabitTitle.text = ""
        holder.binding.txtHabitDescription.text = ""
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    class HabitViewHolder(var binding: HabitListItemBinding):RecyclerView.ViewHolder(binding.root)

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }
}
