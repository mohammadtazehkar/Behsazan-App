package com.example.behsaz.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.behsaz.data.models.signin.SignInCustomerData
import com.example.behsaz.data.models.signin.SignInTokenData

@Database(
    entities = [SignInTokenData::class,SignInCustomerData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getAppDAO():AppDAO
}