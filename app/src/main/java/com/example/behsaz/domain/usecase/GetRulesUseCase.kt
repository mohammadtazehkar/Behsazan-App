package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.message.APIMessageListResponse
import com.example.behsaz.data.models.myService.APIMyServiceListResponse
import com.example.behsaz.data.models.rules.APIGetRulesResponse
import com.example.behsaz.domain.repository.MessagesListRepository
import com.example.behsaz.domain.repository.MyServiceListRepository
import com.example.behsaz.domain.repository.RulesRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class GetRulesUseCase(private val rulesRepository: RulesRepository) {
    suspend fun execute(): Resource<APIGetRulesResponse> = rulesRepository.getRules()
}