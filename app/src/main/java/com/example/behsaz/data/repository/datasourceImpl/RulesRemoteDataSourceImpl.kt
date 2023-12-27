package com.example.behsaz.data.repository.datasourceImpl

import com.example.behsaz.data.api.APIService
import com.example.behsaz.data.models.rules.APIGetRulesResponse
import com.example.behsaz.data.repository.datasource.RulesRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class RulesRemoteDataSourceImpl(
    private val apiService: APIService,
) : RulesRemoteDataSource {
    override suspend fun getRules(): Response<APIGetRulesResponse> {
        return apiService.getRules()
    }
}