package com.example.behsaz.domain.repository

import com.example.behsaz.data.models.signin.APISignInResponse
import com.example.behsaz.utils.Resource

interface SignInRepository {

    suspend fun signIn(username: String, password: String): Resource<APISignInResponse>
}