package com.example.behsaz.data.repository.datasource

import com.example.behsaz.data.models.signin.APISignInResponse
import retrofit2.Response

interface SignInRemoteDataSource {

    suspend fun signIn(username: String, password: String): Response<APISignInResponse>
}