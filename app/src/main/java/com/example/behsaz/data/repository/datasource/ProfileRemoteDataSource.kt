package com.example.behsaz.data.repository.datasource

import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.models.profile.APIUpdateProfileResponse
import com.example.behsaz.data.models.signup.APISignUpResponse
import retrofit2.Response

interface ProfileRemoteDataSource {

    suspend fun getProfileData(
        token: String
    ): Response<APIProfileResponse>
    suspend fun updateProfile(
        token: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        mobileNumber: String,
        username: String,
        password: String,
        email: String
    ): Response<APIUpdateProfileResponse>
}