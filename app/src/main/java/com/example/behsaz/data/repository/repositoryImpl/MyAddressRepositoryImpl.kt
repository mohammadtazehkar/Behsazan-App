package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.myAddress.APIAddAddressResponse
import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.MyAddressRemoteDataSource
import com.example.behsaz.domain.repository.MyAddressRepository
import com.example.behsaz.utils.NetworkUtil
import com.example.behsaz.utils.Resource

class MyAddressRepositoryImpl(
    private val myAddressRemoteDataSource: MyAddressRemoteDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val networkUtil: NetworkUtil
) : MyAddressRepository {

    override suspend fun getMyAddressList(): Resource<APIMyAddressListResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val token = appLocalDataSource.getUserTokenFromDB()
                val response = myAddressRemoteDataSource.getMyAddressList(token)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("An error occurred")
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }
        } else {
            Resource.Error("No internet connection")
        }
    }

    override suspend fun addAddress(
        title: String,
        address: String,
        mapPoint: String
    ): Resource<APIAddAddressResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val token = appLocalDataSource.getUserTokenFromDB()
                val response = myAddressRemoteDataSource.addAddress(token, title, address, mapPoint)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("An error occurred")
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }
        } else {
            Resource.Error("No internet connection")
        }
    }

    override suspend fun updateAddress(
        id: String,
        title: String,
        address: String,
        mapPoint: String
    ): Resource<APIAddAddressResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val token = appLocalDataSource.getUserTokenFromDB()
                val response = myAddressRemoteDataSource.updateAddress(token, id, title, address, mapPoint)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("An error occurred")
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }
        } else {
            Resource.Error("No internet connection")
        }
    }
}