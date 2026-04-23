package com.an.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.an.habittracker.R
import android.widget.Toast
import androidx.navigation.Navigation
import com.an.habittracker.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            if (username == "student" && password == "123") {
                val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
                Navigation.findNavController(it).navigate(action)
            } else {
                Toast.makeText(context, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}