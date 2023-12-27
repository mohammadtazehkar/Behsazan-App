package com.example.behsaz.domain.repository

import com.example.behsaz.data.models.myAddress.APIAddAddressResponse
import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import com.example.behsaz.utils.Resource

interface MyAddressRepository {

    suspend fun getMyAddressList(): Resource<APIMyAddressListResponse>
    suspend fun addAddress(title: String, address: String, mapPoint: String): Resource<APIAddAddressResponse>
    suspend fun updateAddress(id: String, title: String, address: String, mapPoint: String): Resource<APIAddAddressResponse>

}