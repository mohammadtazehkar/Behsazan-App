package com.example.behsaz.data.repository.datasourceImpl

import com.example.behsaz.data.api.APIService
import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.models.profile.APIUpdateProfileResponse
import com.example.behsaz.data.models.signup.APISignUpResponse
import com.example.behsaz.data.repository.datasource.ProfileRemoteDataSource
import com.example.behsaz.data.repository.datasource.SignUpRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class ProfileRemoteDataSourceImpl(
    private val apiService: APIService,
) : ProfileRemoteDataSource {
    override suspend fun getProfileData(token: String): Response<APIProfileResponse> {
        return apiService.profile(token)
    }

    override suspend fun updateProfile(token: String,
                                       firstName: String,
                                       lastName: String,
                                       phoneNumber: String,
                                       mobileNumber: String,
                                       username: String,
                                       password: String,
                                       email: String): Response<APIUpdateProfileResponse> {
        return apiService.updateProfile(token, firstName, lastName, phoneNumber, mobileNumber, username, password, email)
    }

}