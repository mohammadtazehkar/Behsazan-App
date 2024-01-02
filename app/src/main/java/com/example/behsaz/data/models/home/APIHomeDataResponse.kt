package com.example.behsaz.data.models.home

import com.google.gson.annotations.SerializedName

data class APIHomeDataResponse(

    @SerializedName("status")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: CategoriesAndSlidesData?

)
