package com.example.behsaz.domain.repository

import com.example.behsaz.data.models.message.APIMessageListResponse
import com.example.behsaz.utils.Resource

interface MessagesListRepository {

    suspend fun getMessagesList(): Resource<APIMessageListResponse>

}