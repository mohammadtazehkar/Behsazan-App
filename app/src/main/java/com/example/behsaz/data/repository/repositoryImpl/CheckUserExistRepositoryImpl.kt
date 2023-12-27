package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.myAddress.APIAddAddressResponse
import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.MyAddressRemoteDataSource
import com.example.behsaz.domain.repository.CheckUserExistRepository
import com.example.behsaz.domain.repository.MyAddressRepository
import com.example.behsaz.utils.NetworkUtil
import com.example.behsaz.utils.Resource

class CheckUserExistRepositoryImpl(
    private val appLocalDataSource: AppLocalDataSource
) : CheckUserExistRepository {

    override suspend fun check(): Boolean {
        return appLocalDataSource.checkUserExist()
    }
}