package com.example.behsaz.data.repository.datasource

import com.example.behsaz.data.models.myAddress.APIAddAddressResponse
import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import retrofit2.Response

interface MyAddressRemoteDataSource {

    suspend fun getMyAddressList(
        token: String
    ): Response<APIMyAddressListResponse>

    suspend fun addAddress(
        token: String,
        title: String,
        address: String,
        mapPoint: String
    ): Response<APIAddAddressResponse>

    suspend fun updateAddress(
        token: String,
        id: String,
        title: String,
        address: String,
        mapPoint: String
    ): Response<APIAddAddressResponse>

}