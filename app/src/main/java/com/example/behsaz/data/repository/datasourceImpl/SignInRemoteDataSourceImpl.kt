package com.example.behsaz.data.repository.datasourceImpl

import com.example.behsaz.data.api.APIService
import com.example.behsaz.data.models.signin.APISignInResponse
import com.example.behsaz.data.repository.datasource.SignInRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class SignInRemoteDataSourceImpl(
    private val apiService: APIService
): SignInRemoteDataSource {
    override suspend fun signIn(username: String,
                                password: String): Response<APISignInResponse> = apiService.signIn(username,password)

}