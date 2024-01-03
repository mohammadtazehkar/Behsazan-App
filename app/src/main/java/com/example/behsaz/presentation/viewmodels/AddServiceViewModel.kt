package com.example.behsaz.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.behsaz.R
import com.example.behsaz.domain.usecase.AddMyServiceUseCase
import com.example.behsaz.domain.usecase.GetMyAddressListUseCase
import com.example.behsaz.domain.usecase.GetSubCategoryListUseCase
import com.example.behsaz.utils.UIText
import com.example.behsaz.presentation.events.AddServiceEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.states.AddServiceState
import com.example.behsaz.utils.ArgumentKeys.CATEGORY_ID
import com.example.behsaz.utils.ArgumentKeys.CATEGORY_TITLE
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddServiceViewModel @Inject constructor(
    private val addMyServiceUseCase: AddMyServiceUseCase,
    private val getMyAddressListUseCase: GetMyAddressListUseCase,
    private val getSubCategoryListUseCase: GetSubCategoryListUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _addServiceState = mutableStateOf(
        AddServiceState(
            categoryId = savedStateHandle.get<Int>(CATEGORY_ID)!!,
            categoryTitle = savedStateHandle.get<String>(CATEGORY_TITLE)!!,
            address = "",
            latitude = 0.00,
            longitude = 0.00,
            myAddressId = 0,
            description = "",
            myAddressListState = mutableListOf(),
            subCategoryListState = mutableListOf(),
            myAddressListDialogVisible = false,
            subCategoryListDialogVisible = false,
            responseAddService = Resource.Loading(),
            responseMyAddressList = Resource.Loading(),
            responseSubCategoryList = Resource.Loading()
        )
    )
    val addServiceState: State<AddServiceState> = _addServiceState

    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: AddServiceEvent) {
        when (event) {
            is AddServiceEvent.SelectSubCategory -> {
                _addServiceState.value = addServiceState.value.copy(
                    subCategoryId = event.subCategoryId,
                    subCategoryTitle = event.subCategoryTitle,
//                    subCategoryListDialogVisible = false
                )
            }
            is AddServiceEvent.UpdateAddressTextFieldState -> {
                _addServiceState.value = _addServiceState.value.copy(
                    address = event.newValue
                )
            }
            is AddServiceEvent.SelectMyAddress -> {
                _addServiceState.value = addServiceState.value.copy(
                    address = event.address,
                    myAddressId = event.myAddressId,
                    latitude = event.latitude,
                    longitude = event.longitude,
//                    myAddressListDialogVisible = false
                )
            }
            is AddServiceEvent.UpdateDescriptionTextFieldState -> {
                _addServiceState.value = _addServiceState.value.copy(
                    description = event.newValue
                )
            }
            is AddServiceEvent.UpdateMyAddressListDialog -> {
                _addServiceState.value = addServiceState.value.copy(
                    myAddressListDialogVisible = event.status
                )
                if (!event.status){
                    _addServiceState.value = addServiceState.value.copy(
                        responseMyAddressList = Resource.Loading()
                    )
                }
            }
            is AddServiceEvent.UpdateSubCategoryListDialog -> {
                _addServiceState.value = addServiceState.value.copy(
                    subCategoryListDialogVisible = event.status
                )
                if (!event.status){
                    _addServiceState.value = addServiceState.value.copy(
                        responseSubCategoryList = Resource.Loading()
                    )
                }
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
            is AddServiceEvent.PrepareSubCategoryList ->{
                _addServiceState.value = addServiceState.value.copy(
                    subCategoryListState = addServiceState.value.responseSubCategoryList.data?.data!!
                )
            }
            is AddServiceEvent.GetMyAddressList ->{
                getMyAddressList()
            }
            is AddServiceEvent.GetSubCategoryList ->{
                getSubCategoryList()
            }
            is AddServiceEvent.UpdateLoading -> {
                _addServiceState.value = addServiceState.value.copy(
                    isLoading = event.status
                )
            }
            is AddServiceEvent.SuccessfullyAddService -> {
                _addServiceState.value = addServiceState.value.copy(
                    address = "",
                    latitude = 0.00,
                    longitude = 0.00,
                    myAddressId = 0,
                    description = "",
                    myAddressListState = mutableListOf(),
                    subCategoryListState = mutableListOf(),
                    myAddressListDialogVisible = false,
                    subCategoryListDialogVisible = false,
                    responseAddService = Resource.Loading(),
                    responseMyAddressList = Resource.Loading(),
                    responseSubCategoryList = Resource.Loading()
                )
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
                        addServiceState.value.subCategoryId.toString(),
                        addServiceState.value.description
                    )
                )
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

    private fun getSubCategoryList(){
        viewModelScope.launch {
            _addServiceState.value = addServiceState.value.copy(
                responseSubCategoryList = getSubCategoryListUseCase.execute(addServiceState.value.categoryId.toString())
            )
        }
    }
}