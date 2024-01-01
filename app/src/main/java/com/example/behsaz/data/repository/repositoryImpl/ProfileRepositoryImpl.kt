package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.models.profile.APIUpdateProfileResponse
import com.example.behsaz.data.models.profile.ProfileData
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.ProfileRemoteDataSource
import com.example.behsaz.domain.repository.ProfileRepository
import com.example.behsaz.utils.JSonStatusCode.EXPIRED_TOKEN
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
                val token = appLocalDataSource.getUserTokenTypeFromDB() + " " +  appLocalDataSource.getUserTokenFromDB()
                val response = profileRemoteDataSource.getProfileData(token)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    if (response.code() == EXPIRED_TOKEN){
                        appLocalDataSource.deleteUserInfo()
                        appLocalDataSource.deleteUserToken()
                        Resource.Error("expired Token",APIProfileResponse(response.code(),"expired Token", null))
                    }else{
                        Resource.Error("An error occurred")
                    }
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
                val token = appLocalDataSource.getUserTokenTypeFromDB() + " " + appLocalDataSource.getUserTokenFromDB()
                val response = profileRemoteDataSource.updateProfile(token,firstName, lastName, phoneNumber, mobileNumber, username, password, email)
                if (response.isSuccessful && response.body() != null) {
                    appLocalDataSource.saveUserTokenToDB(response.body()?.data?.token!!)
                    appLocalDataSource.saveUserInfoToDB(response.body()?.data?.customer!!)
                    Resource.Success(response.body()!!)
                } else {
                    if (response.code() == EXPIRED_TOKEN){
                        appLocalDataSource.deleteUserInfo()
                        appLocalDataSource.deleteUserToken()
                        Resource.Error("expired Token",APIUpdateProfileResponse(response.code(),"expired Token", null))
                    }else{
                        Resource.Error("An error occurred")
                    }
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }
        } else {
            Resource.Error("No internet connection")
        }
    }
}