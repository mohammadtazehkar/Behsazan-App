package com.example.behsaz.data.repository.datasource

import com.example.behsaz.data.models.rules.APIGetRulesResponse
import retrofit2.Response

interface RulesRemoteDataSource {

    suspend fun getRules(): Response<APIGetRulesResponse>

}