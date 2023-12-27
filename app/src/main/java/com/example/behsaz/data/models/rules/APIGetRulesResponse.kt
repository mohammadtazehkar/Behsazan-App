package com.example.behsaz.data.models.rules

import com.google.gson.annotations.SerializedName

data class APIGetRulesResponse(

    @SerializedName("status")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: String

)
