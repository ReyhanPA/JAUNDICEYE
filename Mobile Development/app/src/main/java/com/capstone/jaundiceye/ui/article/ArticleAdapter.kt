package com.capstone.jaundiceye.ui.article

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.jaundiceye.data.remote.responses.ArticlesResponseItem
import com.capstone.jaundiceye.databinding.ItemArticleBinding
import com.capstone.jaundiceye.util.ArticlesDiffUtilCallback

class ArticleAdapter : ListAdapter<ArticlesResponseItem, ArticleAdapter.ArticleViewHolder>(
    ArticlesDiffUtilCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class ArticleViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesResponseItem){
            binding.textArticleTitle.text = article.title
            binding.textArticleDescription.text = article.description
            binding.buttonRecommendation.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(article.link)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}