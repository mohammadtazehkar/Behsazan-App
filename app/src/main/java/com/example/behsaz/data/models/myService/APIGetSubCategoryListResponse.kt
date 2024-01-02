package com.example.behsaz.data.models.myService

import com.example.behsaz.data.models.home.CategoryListData
import com.google.gson.annotations.SerializedName

data class APIGetSubCategoryListResponse(

    @SerializedName("status")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<SubCategoryListData>?

)
