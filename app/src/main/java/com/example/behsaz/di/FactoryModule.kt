package com.example.behsaz.di

import androidx.lifecycle.SavedStateHandle
import com.example.behsaz.data.models.myAddress.MyAddressListData
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
import com.example.behsaz.domain.usecase.SignInUseCase
import com.example.behsaz.domain.usecase.SignUpUseCase
import com.example.behsaz.domain.usecase.UpdateMyAddressUseCase
import com.example.behsaz.domain.usecase.UpdateProfileDataUseCase
import com.example.behsaz.presentation.viewmodels.AddAddressViewModelFactory
import com.example.behsaz.presentation.viewmodels.AddServiceViewModelFactory
import com.example.behsaz.presentation.viewmodels.HomeViewModelFactory
import com.example.behsaz.presentation.viewmodels.MessageListViewModelFactory
import com.example.behsaz.presentation.viewmodels.MyAddressListViewModelFactory
import com.example.behsaz.presentation.viewmodels.MyServiceListViewModelFactory
import com.example.behsaz.presentation.viewmodels.ProfileViewModelFactory
import com.example.behsaz.presentation.viewmodels.RulesViewModel
import com.example.behsaz.presentation.viewmodels.RulesViewModelFactory
import com.example.behsaz.presentation.viewmodels.SharedViewModel
import com.example.behsaz.presentation.viewmodels.SignInViewModelFactory
import com.example.behsaz.presentation.viewmodels.SignUpViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideSignInViewModelFactory(
        signInUseCase: SignInUseCase
    ): SignInViewModelFactory{
        return SignInViewModelFactory(
            signInUseCase
        )
    }

    @Singleton
    @Provides
    fun provideSignUpViewModelFactory(
        signUpUseCase: SignUpUseCase
    ): SignUpViewModelFactory{
        return SignUpViewModelFactory(
            signUpUseCase
        )
    }

    @Singleton
    @Provides
    fun provideProfileViewModelFactory(
        getProfileDataUseCase: GetProfileDataUseCase,
        updateProfileDataUseCase: UpdateProfileDataUseCase
    ): ProfileViewModelFactory{
        return ProfileViewModelFactory(
            getProfileDataUseCase,
            updateProfileDataUseCase
        )
    }

    @Singleton
    @Provides
    fun provideMyServiceListViewModelFactory(
        getMyServiceListUseCase: GetMyServiceListUseCase
    ): MyServiceListViewModelFactory{
        return MyServiceListViewModelFactory(
            getMyServiceListUseCase
        )
    }

    @Singleton
    @Provides
    fun provideMyAddressListViewModelFactory(
        getMyAddressListUseCase: GetMyAddressListUseCase
    ): MyAddressListViewModelFactory {
        return MyAddressListViewModelFactory(
            getMyAddressListUseCase
        )
    }

    @Singleton
    @Provides
    fun provideAddAddressViewModelFactory(
        addMyAddressUseCase: AddMyAddressUseCase,
        updateMyAddressUseCase: UpdateMyAddressUseCase,
        state: SavedStateHandle
//        item: MyAddressListData,
//        sharedViewModel: SharedViewModel
    ): AddAddressViewModelFactory {
        return AddAddressViewModelFactory(
            addMyAddressUseCase,
            updateMyAddressUseCase,
            state
//            item,
//            sharedViewModel
        )
    }
//
//    @Provides
//    fun provideAddAddressViewModelFactory(factory: AddAddressViewModelFactory.Factory): AddAddressViewModelFactory.Factory {
//        return factory
//    }


    @Singleton
    @Provides
    fun provideMessageListViewModelFactory(
        getMessagesListUseCase: GetMessagesListUseCase,
    ): MessageListViewModelFactory {
        return MessageListViewModelFactory(
            getMessagesListUseCase
        )
    }

    @Singleton
    @Provides
    fun provideRulesViewModelFactory(
        getRulesUseCase: GetRulesUseCase,
    ): RulesViewModelFactory {
        return RulesViewModelFactory(
            getRulesUseCase
        )
    }

    @Singleton
    @Provides
    fun provideAddServiceViewModelFactory(
        addMyServiceUseCase: AddMyServiceUseCase,
        getMyAddressListUseCase: GetMyAddressListUseCase,
        getCategoryListUseCase: GetCategoryListUseCase,
        categoryId: Int,
        categoryTitle: String
    ): AddServiceViewModelFactory {
        return AddServiceViewModelFactory(
            addMyServiceUseCase, getMyAddressListUseCase, getCategoryListUseCase, categoryId, categoryTitle
        )
    }

    @Singleton
    @Provides
    fun provideHomeViewModelFactory(
        getHomeDataUseCase: GetHomeDataUseCase,
    ): HomeViewModelFactory {
        return HomeViewModelFactory(
            getHomeDataUseCase
        )
    }

//    @Singleton
//    @Provides
//    fun provideSplashViewModelFactory(
//        checkUserExistUseCase: CheckUserExistUseCase,
//    ): SplashViewModelFactory {
//        return SplashViewModelFactory(
//            checkUserExistUseCase
//        )
//    }
}