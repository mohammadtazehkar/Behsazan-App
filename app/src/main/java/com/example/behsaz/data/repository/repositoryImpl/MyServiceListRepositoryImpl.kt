package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.myService.APIAddServiceResponse
import com.example.behsaz.data.models.myService.APIGetSubCategoryListResponse
import com.example.behsaz.data.models.myService.APIMyServiceListResponse
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.MyServiceListRemoteDataSource
import com.example.behsaz.domain.repository.MyServiceListRepository
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.NetworkUtil
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.ServerConstants.BASE_URL
import com.example.behsaz.utils.ServerConstants.SUB_URL_CUSTOMER_SERVICE_TYPES

class MyServiceListRepositoryImpl (
    private val myServiceListRemoteDataSource: MyServiceListRemoteDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val networkUtil: NetworkUtil
): MyServiceListRepository {

    override suspend fun getMyServiceList(): Resource<APIMyServiceListResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val token = appLocalDataSource.getUserTokenTypeFromDB() + " " +  appLocalDataSource.getUserTokenFromDB()
                val response = myServiceListRemoteDataSource.getMyServiceList(token)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    if (response.code() == JSonStatusCode.EXPIRED_TOKEN){
                        appLocalDataSource.deleteUserInfo()
                        appLocalDataSource.deleteUserToken()
                        Resource.Error("expired Token", APIMyServiceListResponse(response.code(),"expired Token", null)
                        )
                    }else{
                        Resource.Error("An error occurred",APIMyServiceListResponse(JSonStatusCode.SERVER_CONNECTION,"An error occurred",null))
                    }
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred",APIMyServiceListResponse(JSonStatusCode.SERVER_CONNECTION,"An error occurred",null))
            }
        } else {
            Resource.Error("No internet connection", APIMyServiceListResponse(JSonStatusCode.INTERNET_CONNECTION, "No internet connection",null))
        }
    }

    override suspend fun addService(
        mapPoint: String,
        address: String,
        customerAddressId: String,
        serviceTypeId: String,
        userDescription: String
    ): Resource<APIAddServiceResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val token = appLocalDataSource.getUserTokenTypeFromDB() + " " + appLocalDataSource.getUserTokenFromDB()
                val response = myServiceListRemoteDataSource.addService(token, mapPoint, address, customerAddressId, serviceTypeId, userDescription)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    if (response.code() == JSonStatusCode.EXPIRED_TOKEN){
                        appLocalDataSource.deleteUserInfo()
                        appLocalDataSource.deleteUserToken()
                        Resource.Error("expired Token",APIAddServiceResponse(response.code(),"expired Token"))
                    }else{
                        Resource.Error("An error occurred",APIAddServiceResponse(JSonStatusCode.SERVER_CONNECTION,"An error occurred"))
                    }
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred",APIAddServiceResponse(JSonStatusCode.SERVER_CONNECTION,"An error occurred"))
            }
        } else {
            Resource.Error("No internet connection", APIAddServiceResponse(JSonStatusCode.INTERNET_CONNECTION, "No internet connection"))
        }
    }

    override suspend fun getSubCategoryList(categoryId: String): Resource<APIGetSubCategoryListResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val response = myServiceListRemoteDataSource.getSubCategoryList("$BASE_URL$SUB_URL_CUSTOMER_SERVICE_TYPES$categoryId")
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("An error occurred",APIGetSubCategoryListResponse(JSonStatusCode.SERVER_CONNECTION,"An error occurred",null))
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred",APIGetSubCategoryListResponse(JSonStatusCode.SERVER_CONNECTION,"An error occurred",null))
            }
        } else {
            Resource.Error("No internet connection", APIGetSubCategoryListResponse(JSonStatusCode.INTERNET_CONNECTION, "No internet connection",null))
        }
    }
}