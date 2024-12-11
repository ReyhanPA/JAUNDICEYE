package com.capstone.jaundiceye.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ScannerResponse(

	@field:SerializedName("result")
	val result: String? = null
)
