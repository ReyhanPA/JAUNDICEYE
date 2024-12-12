package com.capstone.jaundiceye.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ScannerResponse(

	@field:SerializedName("result")
	val result: Result? = null
)

data class Result(

	@field:SerializedName("probability")
	val probability: Any? = null,

	@field:SerializedName("label")
	val label: String? = null
)
