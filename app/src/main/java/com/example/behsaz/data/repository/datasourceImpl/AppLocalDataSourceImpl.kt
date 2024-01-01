package com.example.behsaz.data.repository.datasourceImpl

import com.example.behsaz.data.db.AppDAO
import com.example.behsaz.data.models.signin.SignInCustomerData
import com.example.behsaz.data.models.signin.SignInTokenData
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import javax.inject.Inject

class AppLocalDataSourceImpl(
    private val appDAO: AppDAO
): AppLocalDataSource {
    override suspend fun saveUserTokenToDB(signInTokenData: SignInTokenData) {
        appDAO.insertUserToken(signInTokenData)
    }

    override suspend fun saveUserInfoToDB(signInCustomerData: SignInCustomerData) {
        appDAO.insertUserInfo(signInCustomerData)
    }

    override suspend fun getUserTokenFromDB(): String {
        return appDAO.getUserToken()
    }

    override suspend fun getUserTokenTypeFromDB(): String {
        return appDAO.getUserTokenType()
    }

    override suspend fun getUserIdFromDB(): Int {
        return appDAO.getUserId()
    }

    override suspend fun checkUserExist(): Boolean {
        return appDAO.hasUserData() > 0
    }

    override suspend fun getUserFirstNameFromDB(): String {
        return appDAO.getUserFirstName()
    }

    override suspend fun getUserLastNameFromDB(): String {
        return appDAO.getUserLastName()
    }

    override suspend fun deleteUserInfo() {
        appDAO.deleteUserInfo()
    }

    override suspend fun deleteUserToken() {
        appDAO.deleteUserToken()
    }
}