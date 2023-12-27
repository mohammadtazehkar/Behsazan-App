package com.example.behsaz.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.behsaz.data.models.signin.SignInCustomerData
import com.example.behsaz.data.models.signin.SignInTokenData

@Dao
interface AppDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserToken(signInTokenData: SignInTokenData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(signInCustomerData: SignInCustomerData)

    @Query("SELECT accessToken FROM userToken")
    suspend fun getUserToken(): String

    @Query("SELECT userId FROM userInfo")
    suspend fun getUserId(): Int

    @Query("SELECT COUNT(*) FROM userInfo")
    suspend fun hasUserData(): Int
}