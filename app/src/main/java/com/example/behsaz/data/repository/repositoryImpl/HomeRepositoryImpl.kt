package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.home.APIHomeDataResponse
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.HomeRemoteDataSource
import com.example.behsaz.domain.repository.HomeRepository
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.JSonStatusCode.SERVER_CONNECTION
import com.example.behsaz.utils.NetworkUtil
import com.example.behsaz.utils.Resource

class HomeRepositoryImpl(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val networkUtil: NetworkUtil
) : HomeRepository {

    override suspend fun getHomeData(): Resource<APIHomeDataResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val response = homeRemoteDataSource.getHomeData()
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("An error occurred",APIHomeDataResponse(SERVER_CONNECTION,"An error occurred",null))
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred",APIHomeDataResponse(SERVER_CONNECTION,"An error occurred",null))
            }
        } else {
            Resource.Error("No internet connection", APIHomeDataResponse(JSonStatusCode.INTERNET_CONNECTION, "No internet connection",null))
        }
    }

    override suspend fun getUserFullName(): String {
        return appLocalDataSource.getUserFirstNameFromDB() + " " + appLocalDataSource.getUserLastNameFromDB()

    }

    override suspend fun deleteUserData() {
        appLocalDataSource.deleteUserInfo()
        appLocalDataSource.deleteUserToken()
    }
}