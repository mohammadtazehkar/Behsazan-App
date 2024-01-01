package com.example.behsaz.data.models.myAddress

import com.google.gson.annotations.SerializedName

data class APIMyAddressListResponse(

    @SerializedName("status")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<MyAddressListData>?

)
