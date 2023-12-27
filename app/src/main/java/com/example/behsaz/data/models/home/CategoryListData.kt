package com.example.behsaz.data.models.home

import com.example.behsaz.utils.ServerConstants.IMAGE_URL
import com.google.gson.annotations.SerializedName

data class CategoryListData(

    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("imageUrl")
    val imageUrl: String = IMAGE_URL
)
