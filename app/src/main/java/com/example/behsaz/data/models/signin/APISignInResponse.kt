package com.example.behsaz.data.models.signin

import com.google.gson.annotations.SerializedName

data class APISignInResponse(

    @SerializedName("status")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: SignInData

)
