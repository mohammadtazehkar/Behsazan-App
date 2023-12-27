package com.example.behsaz.data.models.home

import com.google.gson.annotations.SerializedName

data class SlideListData(

    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("imageUrl")
    val imageUrl: String

)
