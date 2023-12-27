package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.myService.APIMyServiceListResponse
import com.example.behsaz.domain.repository.MyServiceListRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class GetMyServiceListUseCase(private val myServiceListRepository: MyServiceListRepository) {
    suspend fun execute(): Resource<APIMyServiceListResponse> = myServiceListRepository.getMyServiceList()
}