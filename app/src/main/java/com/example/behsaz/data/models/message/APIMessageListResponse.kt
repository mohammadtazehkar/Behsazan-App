package com.example.behsaz.data.models.message

import com.google.gson.annotations.SerializedName

data class APIMessageListResponse(

    @SerializedName("status")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<MessageListData>?

)
