package com.example.behsaz.data.models.myService

import com.google.gson.annotations.SerializedName

data class SubCategoryListData (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)