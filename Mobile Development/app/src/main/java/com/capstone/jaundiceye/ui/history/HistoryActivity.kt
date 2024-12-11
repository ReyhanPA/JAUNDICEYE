package com.capstone.jaundiceye.ui.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.databinding.ActivityHistoryBinding
import com.capstone.jaundiceye.util.ViewModelFactory

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val viewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(this, "history")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.subheaderText.text = getString(R.string.subheader_history_text)
        binding.toolbarTitle.text = getString(R.string.header_history_text)
        binding.apply {
            toolbarBack.setOnClickListener { finish() }
        }

        adapter = HistoryAdapter()
        binding.rvHistory.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvHistory.layoutManager = layoutManager

        viewModel.getAllHistory().observe(this) {
            adapter.submitList(it)
        }
    }
}