package com.example.behsaz.data.repository.datasourceImpl

import com.example.behsaz.data.api.APIService
import com.example.behsaz.data.models.home.APIHomeDataResponse
import com.example.behsaz.data.repository.datasource.HomeRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class HomeRemoteDataSourceImpl(
    private val apiService: APIService,
) : HomeRemoteDataSource {
    override suspend fun getHomeData(): Response<APIHomeDataResponse> {
        return apiService.getHomeData()
    }
}