package com.example.behsaz.data.repository.datasource

import com.example.behsaz.data.models.myService.APIAddServiceResponse
import com.example.behsaz.data.models.myService.APIGetSubCategoryListResponse
import com.example.behsaz.data.models.myService.APIMyServiceListResponse
import retrofit2.Response

interface MyServiceListRemoteDataSource {

    suspend fun getMyServiceList(
        token: String
    ): Response<APIMyServiceListResponse>

    suspend fun addService(
        token: String,
        mapPoint: String,
        address: String,
        customerAddressId: String,
        serviceTypeId: String,
        userDescription: String
    ): Response<APIAddServiceResponse>

    suspend fun getSubCategoryList(
        url: String
    ): Response<APIGetSubCategoryListResponse>
}