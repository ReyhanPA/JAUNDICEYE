package com.capstone.jaundiceye.ui.recommendation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.jaundiceye.data.remote.responses.HospitalsResponseItem
import com.capstone.jaundiceye.databinding.FragmentRecommendationBinding
import com.capstone.jaundiceye.util.ViewModelFactory

class RecommendationFragment : Fragment() {

    private lateinit var adapter: RecommendationAdapter
    private var _binding: FragmentRecommendationBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<RecommendationViewModel> {
        ViewModelFactory.getInstance(requireActivity(), "hospitals")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHospitals()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = RecommendationAdapter()
        binding?.rvRecommendation?.adapter = adapter
        binding?.rvRecommendation?.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun setupObservers() {
        viewModel.hospitals.observe(viewLifecycleOwner) { hospitals ->
            setHospitalsData(hospitals)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Log.d("RecommendationFragment", "Error: $message")
                showError(message)
            }
        }
    }

    private fun setHospitalsData(hospitals: List<HospitalsResponseItem>) {
        adapter.submitList(hospitals)
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}