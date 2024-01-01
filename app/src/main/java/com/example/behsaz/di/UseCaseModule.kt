package com.example.behsaz.di

import com.example.behsaz.domain.repository.CheckUserExistRepository
import com.example.behsaz.domain.repository.HomeRepository
import com.example.behsaz.domain.repository.MessagesListRepository
import com.example.behsaz.domain.repository.MyAddressRepository
import com.example.behsaz.domain.repository.MyServiceListRepository
import com.example.behsaz.domain.repository.ProfileRepository
import com.example.behsaz.domain.repository.RulesRepository
import com.example.behsaz.domain.repository.SignInRepository
import com.example.behsaz.domain.repository.SignUpRepository
import com.example.behsaz.domain.usecase.AddMyAddressUseCase
import com.example.behsaz.domain.usecase.AddMyServiceUseCase
import com.example.behsaz.domain.usecase.CheckUserExistUseCase
import com.example.behsaz.domain.usecase.GetCategoryListUseCase
import com.example.behsaz.domain.usecase.GetHomeDataUseCase
import com.example.behsaz.domain.usecase.GetMessagesListUseCase
import com.example.behsaz.domain.usecase.GetMyAddressListUseCase
import com.example.behsaz.domain.usecase.GetMyServiceListUseCase
import com.example.behsaz.domain.usecase.GetProfileDataUseCase
import com.example.behsaz.domain.usecase.GetRulesUseCase
import com.example.behsaz.domain.usecase.GetUserFullNameUseCase
import com.example.behsaz.domain.usecase.SignInUseCase
import com.example.behsaz.domain.usecase.SignUpUseCase
import com.example.behsaz.domain.usecase.UpdateMyAddressUseCase
import com.example.behsaz.domain.usecase.UpdateProfileDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideSignInUseCase(
        signInRepository: SignInRepository
    ):SignInUseCase{
        return SignInUseCase(signInRepository)
    }

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        signUpRepository: SignUpRepository
    ):SignUpUseCase{
        return SignUpUseCase(signUpRepository)
    }

    @Singleton
    @Provides
    fun provideGetProfileDataUseCase(
        profileRepository: ProfileRepository
    ):GetProfileDataUseCase{
        return GetProfileDataUseCase(profileRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateProfileDataUseCase(
        profileRepository: ProfileRepository
    ):UpdateProfileDataUseCase{
        return UpdateProfileDataUseCase(profileRepository)
    }

    @Singleton
    @Provides
    fun provideGetMyServiceListUseCase(
        myServiceListRepository: MyServiceListRepository
    ):GetMyServiceListUseCase{
        return GetMyServiceListUseCase(myServiceListRepository)
    }

    @Singleton
    @Provides
    fun provideAddMyServiceUseCase(
        addMyServiceListRepository: MyServiceListRepository
    ): AddMyServiceUseCase {
        return AddMyServiceUseCase(addMyServiceListRepository)
    }

    @Singleton
    @Provides
    fun provideGetMyAddressListUseCase(
        myAddressRepository: MyAddressRepository
    ): GetMyAddressListUseCase {
        return GetMyAddressListUseCase(myAddressRepository)
    }

    @Singleton
    @Provides
    fun provideAddMyAddressUseCase(
        myAddressRepository: MyAddressRepository
    ): AddMyAddressUseCase {
        return AddMyAddressUseCase(myAddressRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateMyAddressUseCase(
        myAddressRepository: MyAddressRepository
    ): UpdateMyAddressUseCase {
        return UpdateMyAddressUseCase(myAddressRepository)
    }

    @Singleton
    @Provides
    fun provideGetMessagesListUseCase(
        messagesListRepository: MessagesListRepository
    ):GetMessagesListUseCase{
        return GetMessagesListUseCase(messagesListRepository)
    }

    @Singleton
    @Provides
    fun provideGetRulesUseCase(
        rulesRepository: RulesRepository
    ):GetRulesUseCase{
        return GetRulesUseCase(rulesRepository)
    }

    @Singleton
    @Provides
    fun provideGetCategoryListUseCase(
        myServiceListRepository: MyServiceListRepository
    ):GetCategoryListUseCase{
        return GetCategoryListUseCase(myServiceListRepository)
    }

    @Singleton
    @Provides
    fun provideGetHomeDataUseCase(
        homeRepository: HomeRepository
    ):GetHomeDataUseCase{
        return GetHomeDataUseCase(homeRepository)
    }

    @Singleton
    @Provides
    fun provideCheckUserExistUseCase(
        checkUserExistRepository: CheckUserExistRepository
    ):CheckUserExistUseCase{
        return CheckUserExistUseCase(checkUserExistRepository)
    }

    @Singleton
    @Provides
    fun provideGetUserFullNameUseCase(
        homeRepository: HomeRepository
    ):GetUserFullNameUseCase{
        return GetUserFullNameUseCase(homeRepository)
    }
}