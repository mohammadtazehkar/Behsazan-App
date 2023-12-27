package com.example.behsaz.data.repository.repositoryImpl

import com.example.behsaz.data.models.signin.APISignInResponse
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.SignInRemoteDataSource
import com.example.behsaz.domain.repository.SignInRepository
import com.example.behsaz.utils.JSonStatusCode.SUCCESS
import com.example.behsaz.utils.NetworkUtil
import com.example.behsaz.utils.Resource

class SignInRepositoryImpl (
    private val signInRemoteDataSource: SignInRemoteDataSource,
    private val appLocalDataSource: AppLocalDataSource,
    private val networkUtil: NetworkUtil
): SignInRepository {
    override suspend fun signIn(username: String, password: String): Resource<APISignInResponse> {
//        return responseToResource(signInRemoteDataSource.signIn())
        return if (networkUtil.isInternetAvailable()) {
            try {
                val response = signInRemoteDataSource.signIn(username, password)
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

//    private fun responseToResource(response: Response<APISignInResponse>):Resource<APISignInResponse>{
//        if (response.isSuccessful){
//            response.body()?.let {result ->
//                return Resource.Success(result)
//            }
//        }
//        return Resource.Error(response.message())
//    }

//    private suspend fun signInFromAPI():APISignInResponse{
//        lateinit var result: APISignInResponse
//        try {
//            val response = signInRemoteDataSource.signIn()
//            val body = response.body()
//            if (body != null){
//                result = body
//            }
//        }catch (exception: Exception){
//            Log.i("mamali",exception.message.toString())
//        }
//        return result
//    }
}