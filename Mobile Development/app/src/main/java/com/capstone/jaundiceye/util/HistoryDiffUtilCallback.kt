package com.capstone.jaundiceye.util

import androidx.recyclerview.widget.DiffUtil
import com.capstone.jaundiceye.data.local.entity.HistoryEntity

object HistoryDiffUtilCallback : DiffUtil.ItemCallback<HistoryEntity>() {
    override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
        val areImageUrisEqual = oldItem.imageUri?.toString() == newItem.imageUri?.toString()
        return oldItem.result == newItem.result &&
                oldItem.inferenceTime == newItem.inferenceTime &&
                areImageUrisEqual
    }
}