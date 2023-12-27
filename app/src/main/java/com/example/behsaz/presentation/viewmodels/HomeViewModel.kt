package com.example.behsaz.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.behsaz.domain.usecase.GetHomeDataUseCase
import com.example.behsaz.presentation.events.HomeEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.states.HomeState
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class HomeViewModel(private val getHomeDataUseCase: GetHomeDataUseCase): ViewModel() {

    private val _homeState = mutableStateOf(
        HomeState(
            categoryListState =
            mutableStateListOf(
//                GroupGridItemData(1, UIText.StringResource(R.string.building_cleaning), R.drawable.cleaner),
//                GroupGridItemData(2, UIText.StringResource(R.string.building_decoration), R.drawable.decor),
//                GroupGridItemData(3, UIText.StringResource(R.string.building_wiring), R.drawable.lump),
//                GroupGridItemData(4, UIText.StringResource(R.string.building_facilities), R.drawable.mechanic),
//                GroupGridItemData(5, UIText.StringResource(R.string.computer_accessory), R.drawable.monitor),
//                GroupGridItemData(6, UIText.StringResource(R.string.transportation), R.drawable.trucking)
            ),
            imageList = mutableStateListOf(
//                "https://football360.ir/_next/image?url=https%3A%2F%2Fstatic.football360.ir%2Fnesta%2Fmedia%2Fposts_media%2Fsteptodown.com335363_5klMXi9.jpg&w=1920&q=75",
//                "https://football360.ir/_next/image?url=https%3A%2F%2Fstatic.football360.ir%2Fnesta%2Fmedia%2Fposts_media%2Fphoto_2023-11-05_10-44-49.jpg&w=1920&q=75",
//                "https://football360.ir/_next/image?url=https%3A%2F%2Fstatic.football360.ir%2Fnesta%2Fmedia%2Fposts_media%2F%25D8%25B1%25D8%25B6%25D8%25A7_%25D8%25AC%25D8%25B9%25D9%2581%25D8%25B1%25DB%258C.jpg&w=1920&q=75",
//                "https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg",
//                "https://football360.ir/_next/image?url=https%3A%2F%2Fstatic.football360.ir%2Fnesta%2Fmedia%2Fposts_media%2F0_Arteta.png&w=1920&q=75",
//                "https://football360.ir/_next/image?url=https%3A%2F%2Fstatic.football360.ir%2Fnesta%2Fmedia%2Fposts_media%2FIMG_9451.jpg&w=1920&q=75",
//                "https://football360.ir/_next/image?url=https%3A%2F%2Fstatic.football360.ir%2Fnesta%2Fmedia%2Fposts_media%2FIMG_0155.JPEG&w=1920&q=75",
            ),
            logoutDialogVisible = false,
            response = Resource.Loading()
        )
    )
    val homeState: State<HomeState> = _homeState

    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            _homeState.value = homeState.value.copy(
                response = getHomeDataUseCase.execute()
            )
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpdateLogoutDialog -> {
                _homeState.value = _homeState.value.copy(
                    logoutDialogVisible = !homeState.value.logoutDialogVisible
                )
            }
            is HomeEvent.PrepareData -> {
                _homeState.value = homeState.value.copy(
                    categoryListState = homeState.value.response.data?.data?.categoryList!!,
                    imageList = homeState.value.response.data?.data?.slideList!!
                )
            }
        }
    }

}

class HomeViewModelFactory(private val getHomeDataUseCase: GetHomeDataUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(getHomeDataUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}