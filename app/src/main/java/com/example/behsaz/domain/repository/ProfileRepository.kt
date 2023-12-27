package com.example.behsaz.domain.repository

import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.models.profile.APIUpdateProfileResponse
import com.example.behsaz.data.models.signin.APISignInResponse
import com.example.behsaz.utils.Resource

interface ProfileRepository {

    suspend fun getProfileData(): Resource<APIProfileResponse>
    suspend fun updateProfile(firstName: String,
                              lastName: String,
                              phoneNumber: String,
                              mobileNumber: String,
                              username: String,
                              password: String,
                              email: String): Resource<APIUpdateProfileResponse>
}