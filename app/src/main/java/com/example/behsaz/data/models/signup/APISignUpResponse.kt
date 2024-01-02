package com.example.behsaz.data.models.signup

import com.google.gson.annotations.SerializedName

data class APISignUpResponse(
    @SerializedName("status")
    val statusCode: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: SignUpData?

)