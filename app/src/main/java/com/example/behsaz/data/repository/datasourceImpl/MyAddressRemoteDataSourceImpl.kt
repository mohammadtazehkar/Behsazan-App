package com.example.behsaz.data.repository.datasourceImpl

import com.example.behsaz.data.api.APIService
import com.example.behsaz.data.models.myAddress.APIAddAddressResponse
import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import com.example.behsaz.data.repository.datasource.MyAddressRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class MyAddressRemoteDataSourceImpl(
    private val apiService: APIService,
) : MyAddressRemoteDataSource {

    override suspend fun getMyAddressList(token: String): Response<APIMyAddressListResponse> {
        return apiService.getMyAddressList(token)
    }

    override suspend fun addAddress(
        token: String,
        title: String,
        address: String,
        mapPoint: String
    ): Response<APIAddAddressResponse> {
        return apiService.addAddress(token, title, address, mapPoint)
    }

    override suspend fun updateAddress(
        token: String,
        id: String,
        title: String,
        address: String,
        mapPoint: String
    ): Response<APIAddAddressResponse> {
        return apiService.editAddress(token, id, title, address, mapPoint)
    }
}