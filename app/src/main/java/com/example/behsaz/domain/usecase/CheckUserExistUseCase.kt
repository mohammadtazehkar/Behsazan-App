package com.example.behsaz.domain.usecase

import com.example.behsaz.domain.repository.CheckUserExistRepository
import javax.inject.Inject

class CheckUserExistUseCase(private val checkUserExistRepository: CheckUserExistRepository) {
    suspend fun execute(): Boolean = checkUserExistRepository.check()

}