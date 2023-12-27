package com.example.behsaz.data.models.signin

import com.google.gson.annotations.SerializedName

data class SignInData(

    @SerializedName("token")
    val token: SignInTokenData,
    @SerializedName("customer")
    val customer: SignInCustomerData

)
