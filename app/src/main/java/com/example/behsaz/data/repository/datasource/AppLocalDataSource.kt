package com.example.behsaz.data.repository.datasource

import com.example.behsaz.data.models.signin.SignInCustomerData
import com.example.behsaz.data.models.signin.SignInTokenData

interface AppLocalDataSource {
    suspend fun saveUserTokenToDB(signInTokenData: SignInTokenData)
    suspend fun saveUserInfoToDB(signInCustomerData: SignInCustomerData)
    suspend fun getUserTokenFromDB(): String
    suspend fun getUserTokenTypeFromDB(): String
    suspend fun getUserIdFromDB(): Int
    suspend fun checkUserExist(): Boolean
    suspend fun getUserFirstNameFromDB(): String
    suspend fun getUserLastNameFromDB(): String

    suspend fun deleteUserInfo()
    suspend fun deleteUserToken()

}