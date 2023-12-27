package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.signin.APISignInResponse
import com.example.behsaz.data.models.signup.APISignUpResponse
import com.example.behsaz.domain.repository.SignInRepository
import com.example.behsaz.domain.repository.SignUpRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class SignUpUseCase(private val signUpRepository: SignUpRepository) {

    suspend fun execute(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        mobileNumber: String,
        username: String,
        password: String,
        email: String,
        reagentToken: String
    ): Resource<APISignUpResponse> = signUpRepository.signUp(
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