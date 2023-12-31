package com.example.behsaz.presentation.states

import com.example.behsaz.data.models.message.APIMessageListResponse
import com.example.behsaz.data.models.message.MessageListData
import com.example.behsaz.utils.Resource


data class MessageListState(
    var isLoading: Boolean = true,
    var listState: List<MessageListData> = mutableListOf(),
    var response: Resource<APIMessageListResponse>
)
