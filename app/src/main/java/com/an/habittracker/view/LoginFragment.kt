package com.an.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.an.habittracker.R
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.an.habittracker.databinding.FragmentLoginBinding
import com.an.habittracker.viewmodel.AuthViewModel

class LoginFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        observeViewModel(view)

        binding.btnLogin.setOnClickListener {
            val username = binding.fieldUsername.text.toString()
            val password = binding.fieldPassword.text.toString()
            viewModel.login(username, password)
        }
    }

    private fun observeViewModel(view: View) {
        viewModel.authSuccessLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
                Navigation.findNavController(view).navigate(action)
                viewModel.reset()
            } else {
                binding.txtUsername.error = viewModel.authErrorLD.value
                binding.txtPassword.error = viewModel.authErrorLD.value
            }
        })
    }
}