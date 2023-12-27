package com.example.behsaz.di

import android.app.Application
import androidx.room.Room
import com.example.behsaz.data.db.AppDAO
import com.example.behsaz.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application):AppDatabase{
        return Room.databaseBuilder(app,AppDatabase::class.java, name = "behsaz_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAppDao(appDatabase: AppDatabase):AppDAO{
        return appDatabase.getAppDAO()
    }
}