package com.example.behsaz.domain.repository

import com.example.behsaz.data.models.myAddress.APIAddAddressResponse
import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import com.example.behsaz.utils.Resource

interface CheckUserExistRepository {

    suspend fun check(): Boolean

}