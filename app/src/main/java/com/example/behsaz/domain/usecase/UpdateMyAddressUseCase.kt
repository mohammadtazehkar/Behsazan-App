package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.myAddress.APIAddAddressResponse
import com.example.behsaz.domain.repository.MyAddressRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class UpdateMyAddressUseCase(private val myAddressRepository: MyAddressRepository) {

    suspend fun execute(
        id: String,
        title: String,
        address: String,
        mapPoint: String
    ): Resource<APIAddAddressResponse> = myAddressRepository.updateAddress(id, title, address, mapPoint)

}