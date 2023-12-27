package com.example.behsaz.data.models.home

import com.google.gson.annotations.SerializedName


data class CategoriesAndSlidesData(
    @SerializedName("serviceGroups")
    val categoryList: List<CategoryListData>,
    @SerializedName("slides")
    val slideList: List<SlideListData>

)