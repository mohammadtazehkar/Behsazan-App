package com.example.behsaz.data.models.profile

import com.google.gson.annotations.SerializedName

data class ProfileData(

    @SerializedName("id")
    val id: Int,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("mobileNumber")
    val mobileNumber: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("reagentToken")
    val reagentToken: String

)
