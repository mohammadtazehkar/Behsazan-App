package com.example.behsaz.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.behsaz.R
import com.example.behsaz.domain.usecase.AddMyAddressUseCase
import com.example.behsaz.domain.usecase.AddMyServiceUseCase
import com.example.behsaz.domain.usecase.GetCategoryListUseCase
import com.example.behsaz.domain.usecase.GetMyAddressListUseCase
import com.example.behsaz.utils.UIText
import com.example.behsaz.presentation.events.AddServiceEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.states.AddServiceState
import com.example.behsaz.utils.ArgumentKeys
import com.example.behsaz.utils.ArgumentKeys.CATEGORY_ID
import com.example.behsaz.utils.ArgumentKeys.CATEGORY_TITLE
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class AddServiceViewModel @Inject constructor(
    private val addMyServiceUseCase: AddMyServiceUseCase,
    private val getMyAddressListUseCase: GetMyAddressListUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val savedStateHandle: SavedStateHandle,
//    categoryId: Int,
//    categoryTitle: String
) : ViewModel() {

    private val _addServiceState = mutableStateOf(
        AddServiceState(
//            categoryId = categoryId,
//            categoryTitle = UIText.StringResource(resId = R.string.select_category),
//            categoryTitle = categoryTitle,
            categoryId = savedStateHandle.get<Int>(CATEGORY_ID)!!,
            categoryTitle = savedStateHandle.get<String>(CATEGORY_TITLE)!!,
            address = "",
            latitude = 0.00,
            longitude = 0.00,
            myAddressId = 0,
            description = "",
            myAddressListState = mutableListOf(
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
            ),
            categoryListState = mutableListOf(
//                CategoryListData(1,"نظافت ساختمان"),
//                CategoryListData(2,"معماری ساختمان"),
//                CategoryListData(3,"سیم کشی ساختمان"),
//                CategoryListData(4,"تاسیسات ساختمان"),
//                CategoryListData(5,"خدمات کامپیوتری"),
//                CategoryListData(6,"اسباب کشی")
            ),
            myAddressListDialogVisible = false,
            categoryListDialogVisible = false,
            responseAddService = Resource.Loading(),
            responseMyAddressList = Resource.Loading(),
            responseCategoryList = Resource.Loading()
        )
    )
    val addServiceState: State<AddServiceState> = _addServiceState

    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: AddServiceEvent) {
        when (event) {
            is AddServiceEvent.SelectCategory -> {
                _addServiceState.value = _addServiceState.value.copy(
                    categoryId = event.categoryId,
//                    categoryTitle = UIText.DynamicString(event.categoryTitle),
                    categoryTitle = event.categoryTitle,
                    categoryListDialogVisible = false
                )
            }
            is AddServiceEvent.UpdateAddressTextFieldState -> {
                _addServiceState.value = _addServiceState.value.copy(
                    address = event.newValue
                )
            }
            is AddServiceEvent.SelectMyAddress -> {
                _addServiceState.value = _addServiceState.value.copy(
                    address = event.address,
                    myAddressId = event.myAddressId,
                    latitude = event.latitude,
                    longitude = event.longitude,
                    myAddressListDialogVisible = false
                )
            }
            is AddServiceEvent.UpdateDescriptionTextFieldState -> {
                _addServiceState.value = _addServiceState.value.copy(
                    description = event.newValue
                )
            }
            is AddServiceEvent.UpdateMyAddressListDialog -> {
                _addServiceState.value = _addServiceState.value.copy(
                    myAddressListDialogVisible = !addServiceState.value.myAddressListDialogVisible
                )
            }
            is AddServiceEvent.UpdateCategoryListDialog -> {
                _addServiceState.value = _addServiceState.value.copy(
                    categoryListDialogVisible = !addServiceState.value.categoryListDialogVisible
                )
            }
            is AddServiceEvent.AddServiceClicked -> {
                _addServiceState.value = addServiceState.value.copy(
                    latitude = event.latitude,
                    longitude = event.longitude
                )
                addService()
            }
            is AddServiceEvent.PrepareMyAddressList ->{
                _addServiceState.value = addServiceState.value.copy(
                    myAddressListState = addServiceState.value.responseMyAddressList.data?.data!!
                )
            }
            is AddServiceEvent.PrepareCategoryList ->{
                _addServiceState.value = addServiceState.value.copy(
                    categoryListState = addServiceState.value.responseCategoryList.data?.data!!
                )
            }
            is AddServiceEvent.GetMyAddressList ->{
                getMyAddressList()
            }
            is AddServiceEvent.GetCategoryList ->{
                getCategoryList()
            }
        }
    }

    private fun addService() {
        if (_addServiceState.value.categoryId == 0) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_category,
                            _addServiceState.value.categoryTitle
                        )
                    )
                )
            }
        }
        else if (_addServiceState.value.address.isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_address,
                            _addServiceState.value.address
                        )
                    )
                )
            }
        }
        else if (_addServiceState.value.latitude == 0.00 && _addServiceState.value.longitude == 0.00) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.please_select_address_from_map,
                            _addServiceState.value.latitude
                        )
                    )
                )
            }
        }
        else if (_addServiceState.value.description.isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_description,
                            _addServiceState.value.description
                        )
                    )
                )
            }
        }
        else {
            viewModelScope.launch {
                _addServiceState.value = addServiceState.value.copy(
                    responseAddService = addMyServiceUseCase.execute(
                        "${addServiceState.value.latitude},${addServiceState.value.longitude}",
                        addServiceState.value.address,
                        addServiceState.value.myAddressId.toString(),
                        addServiceState.value.categoryId.toString(),
                        addServiceState.value.description
                    )
                )
                if (_addServiceState.value.responseAddService.data?.statusCode == JSonStatusCode.SUCCESS){
                    _uiEventFlow.emit(
                        SignInUIEvent.ShowMessage(
                            message = UIText.StringResource(
                                resId = R.string.success_add_service,
                                _addServiceState.value.address
                            )
                        )
                    )
                }
            }
        }
    }

    private fun getMyAddressList(){
        viewModelScope.launch {
            _addServiceState.value = addServiceState.value.copy(
                responseMyAddressList = getMyAddressListUseCase.execute()
            )
        }
    }

    private fun getCategoryList(){
        viewModelScope.launch {
            _addServiceState.value = addServiceState.value.copy(
                responseCategoryList = getCategoryListUseCase.execute(addServiceState.value.categoryId.toString())
            )
        }
    }
}


//class AddServiceViewModelFactory(
//    private val addMyServiceUseCase: AddMyServiceUseCase,
//    private val getMyAddressListUseCase: GetMyAddressListUseCase,
//    private val getCategoryListUseCase: GetCategoryListUseCase,
//    private val categoryId : Int,
//    private val categoryTitle : String) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AddServiceViewModel::class.java)){
//            return AddServiceViewModel(addMyServiceUseCase,getMyAddressListUseCase,getCategoryListUseCase,categoryId,categoryTitle) as T
//        }
//        throw IllegalArgumentException("Unknown View Model Class")
//    }
//}