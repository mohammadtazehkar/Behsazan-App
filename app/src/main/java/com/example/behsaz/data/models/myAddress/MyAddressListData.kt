package com.example.behsaz.data.models.myAddress

import com.google.gson.annotations.SerializedName

data class MyAddressListData(

    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("mapPoint")
    val mapPoint: String,

)
