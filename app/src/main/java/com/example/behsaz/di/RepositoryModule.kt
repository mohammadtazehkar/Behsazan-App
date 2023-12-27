package com.example.behsaz.di

import com.example.behsaz.data.repository.repositoryImpl.MessagesListRepositoryImpl
import com.example.behsaz.data.repository.repositoryImpl.MyAddressRepositoryImpl
import com.example.behsaz.data.repository.repositoryImpl.MyServiceListRepositoryImpl
import com.example.behsaz.data.repository.repositoryImpl.ProfileRepositoryImpl
import com.example.behsaz.data.repository.repositoryImpl.RulesRepositoryImpl
import com.example.behsaz.data.repository.datasource.SignUpRemoteDataSource
import com.example.behsaz.data.repository.repositoryImpl.SignUpRepositoryImpl
import com.example.behsaz.data.repository.datasource.AppLocalDataSource
import com.example.behsaz.data.repository.datasource.HomeRemoteDataSource
import com.example.behsaz.data.repository.datasource.SignInRemoteDataSource
import com.example.behsaz.data.repository.repositoryImpl.SignInRepositoryImpl
import com.example.behsaz.data.repository.datasource.MessageListRemoteDataSource
import com.example.behsaz.data.repository.datasource.MyAddressRemoteDataSource
import com.example.behsaz.data.repository.datasource.MyServiceListRemoteDataSource
import com.example.behsaz.data.repository.datasource.ProfileRemoteDataSource
import com.example.behsaz.data.repository.datasource.RulesRemoteDataSource
import com.example.behsaz.data.repository.repositoryImpl.CheckUserExistRepositoryImpl
import com.example.behsaz.data.repository.repositoryImpl.HomeRepositoryImpl
import com.example.behsaz.domain.repository.CheckUserExistRepository
import com.example.behsaz.domain.repository.HomeRepository
import com.example.behsaz.domain.repository.MessagesListRepository
import com.example.behsaz.domain.repository.MyAddressRepository
import com.example.behsaz.domain.repository.MyServiceListRepository
import com.example.behsaz.domain.repository.ProfileRepository
import com.example.behsaz.domain.repository.RulesRepository
import com.example.behsaz.domain.repository.SignInRepository
import com.example.behsaz.domain.repository.SignUpRepository
import com.example.behsaz.utils.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideSignInRepository(
        signInRemoteDataSource: SignInRemoteDataSource,
        appLocalDataSource: AppLocalDataSource,
        networkUtil: NetworkUtil
    ): SignInRepository{
        return SignInRepositoryImpl(
            signInRemoteDataSource,
            appLocalDataSource,
            networkUtil
        )
    }

    @Singleton
    @Provides
    fun provideSignUpRepository(
        signUpRemoteDataSource: SignUpRemoteDataSource,
        appLocalDataSource: AppLocalDataSource,
        networkUtil: NetworkUtil
    ): SignUpRepository {
        return SignUpRepositoryImpl(
            signUpRemoteDataSource,
            appLocalDataSource,
            networkUtil
        )
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        profileRemoteDataSource: ProfileRemoteDataSource,
        appLocalDataSource: AppLocalDataSource,
        networkUtil: NetworkUtil
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            profileRemoteDataSource,
            appLocalDataSource,
            networkUtil
        )
    }

    @Singleton
    @Provides
    fun provideMyServiceListRepository(
        myServiceListRemoteDataSource: MyServiceListRemoteDataSource,
        appLocalDataSource: AppLocalDataSource,
        networkUtil: NetworkUtil
    ): MyServiceListRepository {
        return MyServiceListRepositoryImpl(
            myServiceListRemoteDataSource,
            appLocalDataSource,
            networkUtil
        )
    }

    @Singleton
    @Provides
    fun provideMyAddressRepository(
        myAddressRemoteDataSource: MyAddressRemoteDataSource,
        appLocalDataSource: AppLocalDataSource,
        networkUtil: NetworkUtil
    ): MyAddressRepository {
        return MyAddressRepositoryImpl(
            myAddressRemoteDataSource,
            appLocalDataSource,
            networkUtil
        )
    }

    @Singleton
    @Provides
    fun provideMessagesListRepository(
        messageListRemoteDataSource: MessageListRemoteDataSource,
        appLocalDataSource: AppLocalDataSource,
        networkUtil: NetworkUtil
    ): MessagesListRepository {
        return MessagesListRepositoryImpl(
            messageListRemoteDataSource,
            appLocalDataSource,
            networkUtil
        )
    }

    @Singleton
    @Provides
    fun provideRulesRepository(
        rulesRemoteDataSource: RulesRemoteDataSource,
        networkUtil: NetworkUtil
    ): RulesRepository {
        return RulesRepositoryImpl(
            rulesRemoteDataSource,
            networkUtil
        )
    }

    @Singleton
    @Provides
    fun provideHomeRepository(
        homeRemoteDataSource: HomeRemoteDataSource,
        networkUtil: NetworkUtil
    ): HomeRepository {
        return HomeRepositoryImpl(
            homeRemoteDataSource,
            networkUtil
        )
    }

    @Singleton
    @Provides
    fun provideCheckUserExistRepository(
        appLocalDataSource: AppLocalDataSource
    ): CheckUserExistRepository {
        return CheckUserExistRepositoryImpl(
            appLocalDataSource
        )
    }
}