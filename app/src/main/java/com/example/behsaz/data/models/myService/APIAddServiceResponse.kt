package com.example.behsaz.data.models.myService

import com.google.gson.annotations.SerializedName

data class APIAddServiceResponse(

    @SerializedName("status")
    val statusCode: Int,
    @SerializedName("message")
    val message: String

)
