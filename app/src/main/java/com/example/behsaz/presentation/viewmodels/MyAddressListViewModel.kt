package com.example.behsaz.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.behsaz.domain.usecase.GetMyAddressListUseCase
import com.example.behsaz.presentation.events.MyAddressListEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.states.MyAddressListState
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class MyAddressListViewModel (private val getMyAddressListUseCase: GetMyAddressListUseCase): ViewModel() {

    private val _myAddressListState = mutableStateOf(
        MyAddressListState(
            fabVisible = true,
            response = Resource.Loading()
//            listState = mutableStateListOf(
//                MyAddressListData(
//                    1,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    2,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554,
//                ),
//                MyAddressListData(
//                    3,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    4,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554,
//                ),
//                MyAddressListData(
//                    5,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    6,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554,
//                ),
//                MyAddressListData(
//                    7,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    8,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    9,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    10,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    11,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    12,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    13,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    14,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    15,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    16,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    17,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    18,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    19,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    20,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    21,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    22,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    23,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    24,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    25,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    26,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    27,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    28,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                ),
//                MyAddressListData(
//                    29,
//                    "خانه",
//                    "اردبیل ، خ امام ، جهازی ، پشت پارک آرتا ، محله حاج حسینعلی ، روبروی مسجد",
//                    38.2543445,
//                    48.3033085
//                ),
//                MyAddressListData(
//                    30,
//                    "شرکت",
//                    "اردبیل ، پا باغمیشه به سمت سعدی ، نرسیده به کبابی قربان ، جنب فرش فروشی پدر ، ساختمان تجاری خدماتی میرداماد ، طبقه 5 واحد 7",
//                    38.246822233000074,
//                    48.278527353327554
//                )
//            )
        )
    )
    val myAddressListState: State<MyAddressListState> = _myAddressListState

    init {
        viewModelScope.launch {
            _myAddressListState.value = myAddressListState.value.copy(
                response = getMyAddressListUseCase.execute()
            )
        }
    }

    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: MyAddressListEvent) {
        when (event) {
            is MyAddressListEvent.MakeFabVisible -> {
                _myAddressListState.value = _myAddressListState.value.copy(
                    fabVisible = true
                )
            }
            is MyAddressListEvent.MakeFabInVisible -> {
                _myAddressListState.value = _myAddressListState.value.copy(
                    fabVisible = false
                )
            }
            is MyAddressListEvent.PrepareList -> {
                _myAddressListState.value = myAddressListState.value.copy(
                    listState = myAddressListState.value.response.data?.data!!
                )
            }
        }
    }
}

class MyAddressListViewModelFactory(private val getMyAddressListUseCase: GetMyAddressListUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAddressListViewModel::class.java)){
            return MyAddressListViewModel(getMyAddressListUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}