package com.example.behsaz.di

import com.example.behsaz.data.api.APIService
import com.example.behsaz.data.repository.datasource.HomeRemoteDataSource
import com.example.behsaz.data.repository.datasource.MessageListRemoteDataSource
import com.example.behsaz.data.repository.datasource.MyAddressRemoteDataSource
import com.example.behsaz.data.repository.datasource.MyServiceListRemoteDataSource
import com.example.behsaz.data.repository.datasource.ProfileRemoteDataSource
import com.example.behsaz.data.repository.datasource.RulesRemoteDataSource
import com.example.behsaz.data.repository.datasource.SignUpRemoteDataSource
import com.example.behsaz.data.repository.datasourceImpl.SignUpRemoteDataSourceImpl
import com.example.behsaz.data.repository.datasource.SignInRemoteDataSource
import com.example.behsaz.data.repository.datasourceImpl.HomeRemoteDataSourceImpl
import com.example.behsaz.data.repository.datasourceImpl.MessagesListRemoteDataSourceImpl
import com.example.behsaz.data.repository.datasourceImpl.MyAddressRemoteDataSourceImpl
import com.example.behsaz.data.repository.datasourceImpl.MyServiceListRemoteDataSourceImpl
import com.example.behsaz.data.repository.datasourceImpl.ProfileRemoteDataSourceImpl
import com.example.behsaz.data.repository.datasourceImpl.RulesRemoteDataSourceImpl
import com.example.behsaz.data.repository.datasourceImpl.SignInRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideSignInRemoteDataSource(
        apiService: APIService
    ): SignInRemoteDataSource {
        return SignInRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideSignUpRemoteDataSource(
        apiService: APIService
    ): SignUpRemoteDataSource {
        return SignUpRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideProfileRemoteDataSource(
        apiService: APIService
    ): ProfileRemoteDataSource {
        return ProfileRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideMyServiceListRemoteDataSource(
        apiService: APIService
    ): MyServiceListRemoteDataSource {
        return MyServiceListRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideMyAddressRemoteDataSource(
        apiService: APIService
    ): MyAddressRemoteDataSource {
        return MyAddressRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideMessagesListRemoteDataSource(
        apiService: APIService
    ): MessageListRemoteDataSource {
        return MessagesListRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideRulesRemoteDataSource(
        apiService: APIService
    ): RulesRemoteDataSource {
        return RulesRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideHomeRemoteDataSource(
        apiService: APIService
    ): HomeRemoteDataSource {
        return HomeRemoteDataSourceImpl(apiService)
    }
}