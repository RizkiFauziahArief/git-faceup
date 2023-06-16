package com.example.faceup.network.models.predict

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("predicted_class")
	val predictedClass: Int? = null,

	@field:SerializedName("class_jerawat")
	val classJerawat: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
