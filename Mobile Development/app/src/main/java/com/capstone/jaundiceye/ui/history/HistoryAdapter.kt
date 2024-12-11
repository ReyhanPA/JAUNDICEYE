package com.capstone.jaundiceye.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.data.local.entity.HistoryEntity
import com.capstone.jaundiceye.databinding.ItemHistoryBinding
import com.capstone.jaundiceye.util.HistoryDiffUtilCallback

class HistoryAdapter: ListAdapter<HistoryEntity, HistoryAdapter.HistoryViewHolder>(HistoryDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
    }

    class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryEntity) {
            Glide.with(binding.root.context)
                .load(history.imageUri)
                .into(binding.imgItemPhoto)
            binding.apply {
                val result = history.result
                val resultHistory = binding.root.context.getString(R.string.result_history, result)
                textHistoryTitle.text = resultHistory
                textHistoryTimestamp.text = history.inferenceTime
            }
        }
    }
}