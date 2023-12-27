package com.example.behsaz.data.models.signin

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "userToken"
)
data class SignInTokenData(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIN: Int

)
