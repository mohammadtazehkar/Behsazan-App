package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.home.APIHomeDataResponse
import com.example.behsaz.data.repository.datasource.HomeRemoteDataSource
import com.example.behsaz.domain.repository.HomeRepository
import com.example.behsaz.utils.NetworkUtil
import com.example.behsaz.utils.Resource

class HomeRepositoryImpl(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val networkUtil: NetworkUtil
) : HomeRepository {

    override suspend fun getHomeData(): Resource<APIHomeDataResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val response = homeRemoteDataSource.getHomeData()
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