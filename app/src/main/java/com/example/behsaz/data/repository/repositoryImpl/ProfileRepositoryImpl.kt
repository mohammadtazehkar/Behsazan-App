package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.models.profile.APIUpdateProfileResponse
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.ProfileRemoteDataSource
import com.example.behsaz.domain.repository.ProfileRepository
import com.example.behsaz.utils.JSonStatusCode.SUCCESS
import com.example.behsaz.utils.NetworkUtil
import com.example.behsaz.utils.Resource

class ProfileRepositoryImpl (
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val networkUtil: NetworkUtil
): ProfileRepository {
    override suspend fun getProfileData(): Resource<APIProfileResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val token = appLocalDataSource.getUserTokenFromDB()
                val response = profileRemoteDataSource.getProfileData(token)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("An error occurred")
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }
        } else {
            Resource.Error("No internet connection")
        }
    }

    override suspend fun updateProfile(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        mobileNumber: String,
        username: String,
        password: String,
        email: String
    ): Resource<APIUpdateProfileResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val token = appLocalDataSource.getUserTokenFromDB()
                val response = profileRemoteDataSource.updateProfile(token,firstName, lastName, phoneNumber, mobileNumber, username, password, email)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("An error occurred")
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }
        } else {
            Resource.Error("No internet connection")
        }
    }
}