package com.example.behsaz.domain.repository

import com.example.behsaz.data.models.myService.APIAddServiceResponse
import com.example.behsaz.data.models.myService.APIGetCategoryListResponse
import com.example.behsaz.data.models.myService.APIMyServiceListResponse
import com.example.behsaz.utils.Resource

interface MyServiceListRepository {

    suspend fun getMyServiceList(): Resource<APIMyServiceListResponse>
    suspend fun addService(mapPoint: String, address: String, customerAddressId: String, serviceTypeId: String, userDescription: String): Resource<APIAddServiceResponse>
    suspend fun getCategoryList(categoryId: String): Resource<APIGetCategoryListResponse>

}