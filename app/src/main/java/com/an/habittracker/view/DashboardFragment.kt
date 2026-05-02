package com.an.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.an.habittracker.databinding.FragmentDashboardBinding
import com.an.habittracker.viewmodel.HabitViewModel

class DashboardFragment : Fragment() {
    private lateinit var viewModel: HabitViewModel
    private lateinit var habitListAdapter: HabitListAdapter
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)

        habitListAdapter = HabitListAdapter(arrayListOf()) { name, newProgress ->
            viewModel.updateHabitProgress(name, newProgress)
        }

        binding.recViewHabit.layoutManager = LinearLayoutManager(context)
        binding.recViewHabit.adapter = habitListAdapter

        binding.fabAddHabit.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToNewHabitFragment()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
        viewModel.loadHabits()
    }

    fun observeViewModel() {
        viewModel.habitsLD.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                habitListAdapter.updateHabitList(it)
            }
        })

        viewModel.habitsLoadErrorLD.observe(viewLifecycleOwner, Observer {
            binding.txtError.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.recViewHabit.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recViewHabit.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
    }
}