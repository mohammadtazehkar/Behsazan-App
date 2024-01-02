package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.message.APIMessageListResponse
import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.MessageListRemoteDataSource
import com.example.behsaz.domain.repository.MessagesListRepository
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.NetworkUtil
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.ServerConstants.BASE_URL
import com.example.behsaz.utils.ServerConstants.SUB_URL_MESSAGES

class MessagesListRepositoryImpl (
    private val messageListRemoteDataSource: MessageListRemoteDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val networkUtil: NetworkUtil
): MessagesListRepository {
    override suspend fun getMessagesList(): Resource<APIMessageListResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val userId = appLocalDataSource.getUserIdFromDB()
                val response = messageListRemoteDataSource.getMessagesList("$BASE_URL$SUB_URL_MESSAGES$userId")
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("An error occurred",APIMessageListResponse(JSonStatusCode.SERVER_CONNECTION,"An error occurred",null))
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred",APIMessageListResponse(JSonStatusCode.SERVER_CONNECTION,"An error occurred",null))
            }
        } else {
            Resource.Error("No internet connection", APIMessageListResponse(JSonStatusCode.INTERNET_CONNECTION, "No internet connection",null))
        }
    }
}