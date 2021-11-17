package com.test.tix.core.app.model

import com.google.gson.annotations.SerializedName

data class Permissions(

	@field:SerializedName("pull")
	val pull: Boolean? = null,

	@field:SerializedName("maintain")
	val maintain: Boolean? = null,

	@field:SerializedName("admin")
	val admin: Boolean? = null,

	@field:SerializedName("triage")
	val triage: Boolean? = null,

	@field:SerializedName("push")
	val push: Boolean? = null
)