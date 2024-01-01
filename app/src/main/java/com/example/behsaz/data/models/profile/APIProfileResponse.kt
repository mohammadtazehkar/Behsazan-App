package com.example.behsaz.data.models.profile

import com.google.gson.annotations.SerializedName

data class APIProfileResponse(
    @SerializedName("status")
    val statusCode: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: ProfileData?

)