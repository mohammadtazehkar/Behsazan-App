package com.example.behsaz.data.models.myService

import com.google.gson.annotations.SerializedName

data class MyServiceListData(

    @SerializedName("id")
    val id: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("dateTime")
    val dateTime: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("serviceGroup")
    val serviceGroup: String,
    @SerializedName("serviceType")
    val serviceType: String

)
