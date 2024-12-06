package com.capstone.jaundiceye.util

import androidx.recyclerview.widget.DiffUtil
import com.capstone.jaundiceye.data.remote.responses.ArticlesResponseItem

object ArticlesDiffUtilCallback : DiffUtil.ItemCallback<ArticlesResponseItem>() {
    override fun areItemsTheSame(oldItem: ArticlesResponseItem, newItem: ArticlesResponseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticlesResponseItem, newItem: ArticlesResponseItem): Boolean {
        return oldItem == newItem
    }
}