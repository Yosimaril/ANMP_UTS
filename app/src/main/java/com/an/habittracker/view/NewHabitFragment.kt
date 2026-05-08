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
import com.an.habittracker.viewmodel.HabitViewModel

class NewHabitFragment : Fragment() {
    private lateinit var binding: FragmentNewHabitBinding
    private lateinit var viewModel: HabitViewModel

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
        binding = FragmentNewHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, iconNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerNewHabitImage.adapter = adapter

        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)
        observeViewModel()

        binding.btnCreateHabit.setOnClickListener {
            viewModel.validateHabit(
                name = binding.txtNewHabitName.editText?.text.toString(),
                description = binding.txtNewHabitDescription.editText?.text.toString(),
                goalString = binding.txtNewHabitGoal.editText?.text.toString(),
                unit = binding.txtNewHabitUnit.editText?.text.toString(),
                iconResId = iconList[binding.spinnerNewHabitImage.selectedItemPosition]
            )
        }
    }

    private fun observeViewModel() {
        viewModel.formStateLD.observe(viewLifecycleOwner) { state ->
            binding.txtNewHabitName.error = state.nameError
            binding.txtNewHabitDescription.error = state.descriptionError
            binding.txtNewHabitGoal.error = state.goalError
            binding.txtNewHabitUnit.error = state.unitError

            if (state.isValid) {
                viewModel.resetFormState()
                Navigation.findNavController(requireView()).navigateUp()
            }
        }
    }
}