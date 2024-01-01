package com.example.behsaz.data.models.myService

import com.google.gson.annotations.SerializedName

data class APIMyServiceListResponse(

    @SerializedName("status")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<MyServiceListData>?

)
