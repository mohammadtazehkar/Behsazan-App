package com.example.behsaz.domain.repository

import com.example.behsaz.data.models.signup.APISignUpResponse
import com.example.behsaz.utils.Resource

interface SignUpRepository {

    suspend fun signUp(firstName: String,
                       lastName: String,
                       phoneNumber: String,
                       mobileNumber: String,
                       username: String,
                       password: String,
                       email: String,
                       reagentToken: String): Resource<APISignUpResponse>
}