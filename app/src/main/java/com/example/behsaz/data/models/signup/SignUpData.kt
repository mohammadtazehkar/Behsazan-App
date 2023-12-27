package com.example.behsaz.data.models.signup

import com.example.behsaz.data.models.signin.SignInCustomerData
import com.example.behsaz.data.models.signin.SignInTokenData
import com.google.gson.annotations.SerializedName

data class SignUpData(

    @SerializedName("token")
    val token: SignInTokenData,
    @SerializedName("customer")
    val customer: SignInCustomerData

)
