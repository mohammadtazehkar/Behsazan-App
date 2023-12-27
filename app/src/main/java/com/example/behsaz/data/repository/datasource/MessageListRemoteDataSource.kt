package com.example.behsaz.data.repository.datasource

import com.example.behsaz.data.models.message.APIMessageListResponse
import retrofit2.Response

interface MessageListRemoteDataSource {

    suspend fun getMessagesList(
        url: String
    ): Response<APIMessageListResponse>

}