package com.example.behsaz.di

import com.example.behsaz.data.db.AppDAO
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasourceImpl.AppLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideAppLocalDataSource(appDAO: AppDAO): AppLocalDataSource {
        return AppLocalDataSourceImpl(appDAO)
    }
}