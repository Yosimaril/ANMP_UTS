package com.an.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.an.habittracker.R
import androidx.navigation.Navigation
import com.an.habittracker.databinding.FragmentNewHabitBinding
import com.an.habittracker.model.Habit
import com.an.habittracker.viewmodel.ListViewModel

class NewHabitFragment : Fragment() {
    private lateinit var binding: FragmentNewHabitBinding
    private lateinit var viewModel: ListViewModel

    private val iconList = listOf(
        R.drawable.ic_grocery_store,
        R.drawable.ic_happy,
        R.drawable.ic_money,
        R.drawable.ic_music,
        R.drawable.ic_run,
        R.drawable.ic_sleep,
        R.drawable.ic_water,
        R.drawable.ic_workout,
    )

    private val iconNames = listOf(
        "Trolley",
        "Happy Face",
        "Money",
        "Music",
        "Run",
        "Sleep",
        "Water",
        "Workout",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, iconNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerNewHabitImage.adapter = adapter

        viewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)

        binding.btnCreateHabit.setOnClickListener {
            val name = binding.txtNewHabitName.editText?.text.toString()
            val description = binding.txtNewHabitDescription.editText?.text.toString()
            val goalStr = binding.txtNewHabitGoal.editText?.text.toString()
            val unit = binding.txtNewHabitUnit.editText?.text.toString()
            val selectedIcon = iconList[binding.spinnerNewHabitImage.selectedItemPosition]

            var isValid = true

            binding.txtNewHabitName.error = null
            binding.txtNewHabitDescription.error = null
            binding.txtNewHabitGoal.error = null
            binding.txtNewHabitUnit.error = null

            if (name.isEmpty()) {
                binding.txtNewHabitName.error = "Name required"
                isValid = false
            }

            if (description.isEmpty()) {
                binding.txtNewHabitDescription.error = "Description required"
                isValid = false
            }

            val goal = goalStr.toIntOrNull()
            if (goal == null || goal <= 0) {
                binding.txtNewHabitGoal.error = "Invalid goal"
                isValid = false
            }

            if (unit.isEmpty()) {
                binding.txtNewHabitUnit.error = "Unit required"
                isValid = false
            }

            if (isValid) {
                val habit = Habit(
                    name = name,
                    description = description,
                    goal = goal ?: 0,
                    progress = 0,
                    unit = unit,
                    iconResId = selectedIcon
                )

                viewModel.addHabit(habit)
                Navigation.findNavController(it).navigateUp()
            }
        }
    }
}