package com.example.behsaz.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

//    @Singleton
//    @Provides
//    fun provideAddAddressViewModelFactory(
//        addMyAddressUseCase: AddMyAddressUseCase,
//        updateMyAddressUseCase: UpdateMyAddressUseCase,
//        state: SavedStateHandle
////        item: MyAddressListData,
////        sharedViewModel: SharedViewModel
//    ): AddAddressViewModelFactory {
//        return AddAddressViewModelFactory(
//            addMyAddressUseCase,
//            updateMyAddressUseCase,
//            state
////            item,
////            sharedViewModel
//        )
//    }
//
//    @Provides
//    fun provideAddAddressViewModelFactory(factory: AddAddressViewModelFactory.Factory): AddAddressViewModelFactory.Factory {
//        return factory
//    }

//    @Singleton
//    @Provides
//    fun provideRulesViewModelFactory(
//        getRulesUseCase: GetRulesUseCase,
//    ): RulesViewModelFactory {
//        return RulesViewModelFactory(
//            getRulesUseCase
//        )
//    }

//    @Singleton
//    @Provides
//    fun provideAddServiceViewModelFactory(
//        addMyServiceUseCase: AddMyServiceUseCase,
//        getMyAddressListUseCase: GetMyAddressListUseCase,
//        getCategoryListUseCase: GetCategoryListUseCase,
//        categoryId: Int,
//        categoryTitle: String
//    ): AddServiceViewModelFactory {
//        return AddServiceViewModelFactory(
//            addMyServiceUseCase, getMyAddressListUseCase, getCategoryListUseCase, categoryId, categoryTitle
//        )
//    }
}