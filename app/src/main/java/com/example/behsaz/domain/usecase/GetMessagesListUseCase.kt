package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.message.APIMessageListResponse
import com.example.behsaz.data.models.myService.APIMyServiceListResponse
import com.example.behsaz.domain.repository.MessagesListRepository
import com.example.behsaz.domain.repository.MyServiceListRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class GetMessagesListUseCase(private val messagesListRepository: MessagesListRepository) {
    suspend fun execute(): Resource<APIMessageListResponse> = messagesListRepository.getMessagesList()
}