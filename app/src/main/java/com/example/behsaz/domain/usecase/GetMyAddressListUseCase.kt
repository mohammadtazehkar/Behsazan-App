package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import com.example.behsaz.domain.repository.MyAddressRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class GetMyAddressListUseCase(private val myAddressRepository: MyAddressRepository) {
    suspend fun execute(): Resource<APIMyAddressListResponse> = myAddressRepository.getMyAddressList()
}