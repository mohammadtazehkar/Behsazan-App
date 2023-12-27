package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.signin.APISignInResponse
import com.example.behsaz.domain.repository.SignInRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class SignInUseCase(private val signInRepository: SignInRepository) {

    suspend fun execute(username: String, password: String):Resource<APISignInResponse> = signInRepository.signIn(username,password)
}