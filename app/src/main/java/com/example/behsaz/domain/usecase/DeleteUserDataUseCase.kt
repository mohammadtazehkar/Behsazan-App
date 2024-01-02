package com.example.behsaz.domain.usecase

import com.example.behsaz.domain.repository.CheckUserExistRepository
import com.example.behsaz.domain.repository.HomeRepository
import javax.inject.Inject

class DeleteUserDataUseCase(private val homeRepository: HomeRepository) {
    suspend fun execute() = homeRepository.deleteUserData()

}