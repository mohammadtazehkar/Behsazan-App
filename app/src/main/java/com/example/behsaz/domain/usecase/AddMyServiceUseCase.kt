package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.myAddress.APIAddAddressResponse
import com.example.behsaz.data.models.myService.APIAddServiceResponse
import com.example.behsaz.domain.repository.MyAddressRepository
import com.example.behsaz.domain.repository.MyServiceListRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class AddMyServiceUseCase(private val myServiceListRepository: MyServiceListRepository) {

    suspend fun execute(mapPoint: String, address: String, customerAddressId: String, serviceTypeId: String, userDescription: String)
    : Resource<APIAddServiceResponse> = myServiceListRepository.addService(mapPoint, address, customerAddressId, serviceTypeId, userDescription)

}