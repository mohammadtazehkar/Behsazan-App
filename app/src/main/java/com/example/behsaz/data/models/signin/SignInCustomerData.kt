package com.example.behsaz.data.models.signin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(
    tableName = "userInfo"
)
data class SignInCustomerData(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "userId")
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
