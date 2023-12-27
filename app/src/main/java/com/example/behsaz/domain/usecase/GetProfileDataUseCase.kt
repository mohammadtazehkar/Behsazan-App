package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.domain.repository.ProfileRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class GetProfileDataUseCase(private val profileRepository: ProfileRepository) {
    suspend fun execute(): Resource<APIProfileResponse> = profileRepository.getProfileData()
}