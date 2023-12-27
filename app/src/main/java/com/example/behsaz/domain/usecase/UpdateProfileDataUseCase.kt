package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.models.profile.APIUpdateProfileResponse
import com.example.behsaz.domain.repository.ProfileRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class UpdateProfileDataUseCase(private val profileRepository: ProfileRepository) {
    suspend fun execute(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        mobileNumber: String,
        username: String,
        password: String,
        email: String
    ): Resource<APIUpdateProfileResponse> = profileRepository.updateProfile(
        firstName,
        lastName,
        phoneNumber,
        mobileNumber,
        username,
        password,
        email
    )
}