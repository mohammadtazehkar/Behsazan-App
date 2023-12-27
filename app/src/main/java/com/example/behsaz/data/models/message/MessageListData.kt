package com.example.behsaz.data.models.message

import com.google.gson.annotations.SerializedName


data class MessageListData(

    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("dateTime")
    val dateTime: String

)