package com.capstone.jaundiceye.data.remote.responses

import com.google.gson.annotations.SerializedName

data class HospitalsResponse(

	@field:SerializedName("HospitalsResponse")
	val hospitalsResponse: List<HospitalsResponseItem?>? = null
)

data class HospitalsResponseItem(

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
