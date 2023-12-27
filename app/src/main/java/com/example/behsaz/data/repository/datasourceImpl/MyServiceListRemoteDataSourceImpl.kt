package com.example.behsaz.data.repository.datasourceImpl

import com.example.behsaz.data.api.APIService
import com.example.behsaz.data.models.myService.APIAddServiceResponse
import com.example.behsaz.data.models.myService.APIGetCategoryListResponse
import com.example.behsaz.data.models.myService.APIMyServiceListResponse
import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.models.profile.APIUpdateProfileResponse
import com.example.behsaz.data.models.signup.APISignUpResponse
import com.example.behsaz.data.repository.datasource.MyServiceListRemoteDataSource
import com.example.behsaz.data.repository.datasource.ProfileRemoteDataSource
import com.example.behsaz.data.repository.datasource.SignUpRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class MyServiceListRemoteDataSourceImpl(
    private val apiService: APIService,
) : MyServiceListRemoteDataSource {
    override suspend fun getMyServiceList(token: String): Response<APIMyServiceListResponse> {
        return apiService.getMyServiceList(token)
    }

    override suspend fun addService(
        token: String,
        mapPoint: String,
        address: String,
        customerAddressId: String,
        serviceTypeId: String,
        userDescription: String
    ): Response<APIAddServiceResponse> {
        return apiService.addService(token, mapPoint, address, customerAddressId, serviceTypeId, userDescription)
    }

    override suspend fun getCategoryList(url: String): Response<APIGetCategoryListResponse> {
        return apiService.getCategoryList(url)
    }
}