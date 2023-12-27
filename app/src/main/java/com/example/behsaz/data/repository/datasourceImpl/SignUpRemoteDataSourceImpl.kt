package com.example.behsaz.data.repository.datasourceImpl

import com.example.behsaz.data.api.APIService
import com.example.behsaz.data.models.signup.APISignUpResponse
import com.example.behsaz.data.repository.datasource.SignUpRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class SignUpRemoteDataSourceImpl(
    private val apiService: APIService
) : SignUpRemoteDataSource {
    override suspend fun signUp(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        mobileNumber: String,
        username: String,
        password: String,
        email: String,
        reagentToken: String
    ): Response<APISignUpResponse> = apiService.signUp(
        firstName,
        lastName,
        phoneNumber,
        mobileNumber,
        username,
        password,
        email,
        reagentToken
    )

}