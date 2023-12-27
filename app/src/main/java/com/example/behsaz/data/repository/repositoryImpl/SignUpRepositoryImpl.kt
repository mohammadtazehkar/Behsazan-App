package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.signup.APISignUpResponse
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.SignUpRemoteDataSource
import com.example.behsaz.domain.repository.SignUpRepository
import com.example.behsaz.utils.JSonStatusCode.SUCCESS
import com.example.behsaz.utils.NetworkUtil
import com.example.behsaz.utils.Resource

class SignUpRepositoryImpl (
    private val signUpRemoteDataSource: SignUpRemoteDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val networkUtil: NetworkUtil
): SignUpRepository {
    override suspend fun signUp(firstName: String,
                                lastName: String,
                                phoneNumber: String,
                                mobileNumber: String,
                                username: String,
                                password: String,
                                email: String,
                                reagentToken: String): Resource<APISignUpResponse> {
        return if (networkUtil.isInternetAvailable()) {
            try {
                val response = signUpRemoteDataSource.signUp(firstName, lastName, phoneNumber, mobileNumber, username, password, email, reagentToken)
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()?.statusCode == SUCCESS){
                        appLocalDataSource.saveUserTokenToDB(response.body()?.data?.token!!)
                        appLocalDataSource.saveUserInfoToDB(response.body()?.data?.customer!!)
                    }
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