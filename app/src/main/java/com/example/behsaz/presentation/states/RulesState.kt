package com.example.behsaz.presentation.states

import com.example.behsaz.data.models.message.APIMessageListResponse
import com.example.behsaz.data.models.message.MessageListData
import com.example.behsaz.data.models.rules.APIGetRulesResponse
import com.example.behsaz.utils.Resource


data class RulesState(
    var isLoading: Boolean = false,
    var url: String,
    var response: Resource<APIGetRulesResponse>
)
