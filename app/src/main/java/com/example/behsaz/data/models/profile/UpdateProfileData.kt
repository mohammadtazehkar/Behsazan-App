package com.example.behsaz.data.models.profile

import com.example.behsaz.data.models.signin.SignInCustomerData
import com.example.behsaz.data.models.signin.SignInTokenData
import com.google.gson.annotations.SerializedName

data class UpdateProfileData (
    @SerializedName("token")
    val token: SignInTokenData,
    @SerializedName("customer")
    val customer: SignInCustomerData
)