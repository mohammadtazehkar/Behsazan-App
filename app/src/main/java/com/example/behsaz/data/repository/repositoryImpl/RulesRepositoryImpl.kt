package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.message.APIMessageListResponse
import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.models.rules.APIGetRulesResponse
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.MessageListRemoteDataSource
import com.example.behsaz.data.repository.datasource.RulesRemoteDataSource
import com.example.behsaz.domain.repository.MessagesListRepository
import com.example.behsaz.domain.repository.RulesRepository
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.NetworkUtil
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.ServerConstants.BASE_URL
import com.example.behsaz.utils.ServerConstants.SUB_URL_MESSAGES

class RulesRepositoryImpl (
    private val rulesRemoteDataSource: RulesRemoteDataSource,
    private val networkUtil: NetworkUtil
): RulesRepository {
    override suspend fun getRules(): Resource<APIGetRulesResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val response = rulesRemoteDataSource.getRules()
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("An error occurred",APIGetRulesResponse(JSonStatusCode.SERVER_CONNECTION,"An error occurred",null))
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred",APIGetRulesResponse(JSonStatusCode.SERVER_CONNECTION,"An error occurred",null))
            }
        } else {
            Resource.Error("No internet connection", APIGetRulesResponse(JSonStatusCode.INTERNET_CONNECTION, "No internet connection",null))
        }
    }
}