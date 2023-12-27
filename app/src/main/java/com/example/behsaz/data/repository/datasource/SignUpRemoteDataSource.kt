package com.example.behsaz.data.repository.datasource

import com.example.behsaz.data.models.signup.APISignUpResponse
import retrofit2.Response

interface SignUpRemoteDataSource {

    suspend fun signUp(firstName: String,
                       lastName: String,
                       phoneNumber: String,
                       mobileNumber: String,
                       username: String,
                       password: String,
                       email: String,
                       reagentToken: String): Response<APISignUpResponse>
}