package com.capstone.jaundiceye.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.jaundiceye.databinding.FragmentProfileBinding
import com.capstone.jaundiceye.util.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity(), "user")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { userModel ->
            binding?.profileName?.text = userModel.username
        }
        binding?.btnLogout?.setOnClickListener { viewModel.logout() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}