package com.capstone.jaundiceye.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(

	@field:SerializedName("ArticlesResponse")
	val articlesResponse: List<ArticlesResponseItem?>? = null
)

data class ArticlesResponseItem(

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
)
