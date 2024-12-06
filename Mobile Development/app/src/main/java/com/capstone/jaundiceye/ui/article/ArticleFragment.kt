package com.capstone.jaundiceye.ui.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.jaundiceye.data.remote.responses.ArticlesResponseItem
import com.capstone.jaundiceye.databinding.FragmentArticleBinding
import com.capstone.jaundiceye.util.ViewModelFactory

class ArticleFragment : Fragment() {

    private lateinit var adapter: ArticleAdapter
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<ArticleViewModel> {
        ViewModelFactory.getInstance(requireActivity(), "articles")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getArticles()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter()
        binding?.rvArticle?.adapter = adapter
        binding?.rvArticle?.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun setupObservers() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            setArticlesData(articles)
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

    private fun setArticlesData(articles: List<ArticlesResponseItem>) {
        adapter.submitList(articles)
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}