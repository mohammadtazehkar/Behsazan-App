package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.home.APIHomeDataResponse
import com.example.behsaz.domain.repository.HomeRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class GetHomeDataUseCase (private val homeRepository: HomeRepository) {
    suspend fun execute(): Resource<APIHomeDataResponse> = homeRepository.getHomeData()
}