package com.example.behsaz.data.repository.datasource

import com.example.behsaz.data.models.home.APIHomeDataResponse
import retrofit2.Response

interface HomeRemoteDataSource {

    suspend fun getHomeData(): Response<APIHomeDataResponse>
}