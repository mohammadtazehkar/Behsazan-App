package com.example.behsaz.domain.repository

import com.example.behsaz.data.models.home.APIHomeDataResponse
import com.example.behsaz.utils.Resource

interface HomeRepository {

    suspend fun getHomeData(): Resource<APIHomeDataResponse>
    suspend fun getUserFullName(): String
    suspend fun deleteUserData()


}