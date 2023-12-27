package com.example.behsaz.data.repository.datasourceImpl

import com.example.behsaz.data.api.APIService
import com.example.behsaz.data.models.message.APIMessageListResponse
import com.example.behsaz.data.repository.datasource.MessageListRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class MessagesListRemoteDataSourceImpl(
    private val apiService: APIService,
) : MessageListRemoteDataSource {
    override suspend fun getMessagesList(url: String): Response<APIMessageListResponse> {
        return apiService.getMessagesList(url)
    }
}