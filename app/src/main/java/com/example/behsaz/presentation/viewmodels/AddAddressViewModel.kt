package com.example.behsaz.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.behsaz.R
import com.example.behsaz.data.models.myAddress.MyAddressListData
import com.example.behsaz.domain.usecase.AddMyAddressUseCase
import com.example.behsaz.domain.usecase.UpdateMyAddressUseCase
import com.example.behsaz.utils.UIText
import com.example.behsaz.presentation.events.AddAddressEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.states.AddAddressState
import com.example.behsaz.utils.ArgumentKeys
import com.example.behsaz.utils.Constants.FOR_ADD
import com.example.behsaz.utils.Constants.FOR_EDIT
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val addMyAddressUseCase: AddMyAddressUseCase,
    private val updateMyAddressUseCase: UpdateMyAddressUseCase,
    private val savedStateHandle: SavedStateHandle,
//    item : MyAddressListData,
) : ViewModel() {
    private var myAddressItem :MyAddressListData =
        if (savedStateHandle.get<Int>(ArgumentKeys.ADDRESS_ID)!! != 0){
            MyAddressListData(
                savedStateHandle.get<Int>(ArgumentKeys.ADDRESS_ID)!!,
                savedStateHandle.get<String>(ArgumentKeys.ADDRESS_TITLE)!!,
                savedStateHandle.get<String>(ArgumentKeys.ADDRESS)!!,
                "${savedStateHandle.get<String>(ArgumentKeys.LATITUDE)!!},${savedStateHandle.get<String>(ArgumentKeys.LONGITUDE)!!}"
            )
        }else{
            MyAddressListData(0,"","", "0.00,0.00")
        }
    private val _addAddressState = mutableStateOf(
        AddAddressState(
            id = myAddressItem.id,
            title = myAddressItem.title,
            address = myAddressItem.address,
            latitude = if (myAddressItem.id == 0) {
                0.00
            }else{
//                item.mapPoint.split(',')[0].toDouble()
                myAddressItem.mapPoint.split(',')[0].toDouble()
            },
            longitude = if (myAddressItem.id == 0) {
                0.00
            }else{
                myAddressItem.mapPoint.split(',')[1].toDouble()
            },
            forWhat = if (myAddressItem.id == 0) {
                FOR_ADD
            }else{
                FOR_EDIT
            },
            actionTitleId = if (myAddressItem.id == 0) {
                R.string.add_address
            }else{
                R.string.edit_address
            },
            response = Resource.Loading()
        )
    )
    val addAddressState: State<AddAddressState> = _addAddressState

    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: AddAddressEvent) {
        when (event) {
            is AddAddressEvent.UpdateTitleTextFieldState -> {
                _addAddressState.value = _addAddressState.value.copy(
                    title = event.newValue
                )
            }
            is AddAddressEvent.UpdateAddressTextFieldState -> {
                _addAddressState.value = _addAddressState.value.copy(
                    address = event.newValue
                )
            }
            is AddAddressEvent.UpdateSelectedLocation -> {
                _addAddressState.value = addAddressState.value.copy(
                    latitude = event.latitude,
                    longitude = event.longitude
                )
            }
            is AddAddressEvent.AddAddressClicked -> {
                addAddress()
            }
            is AddAddressEvent.UpdateLoading -> {
                _addAddressState.value = addAddressState.value.copy(
                    isLoading = event.status
                )
            }
        }
    }


    private fun addAddress() {
        if (_addAddressState.value.title.isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_title,
                            _addAddressState.value.title
                        )
                    )
                )
            }
        }
        else if (_addAddressState.value.address.isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_address,
                            _addAddressState.value.address
                        )
                    )
                )
            }
        }
        else if (_addAddressState.value.latitude == 0.00 && _addAddressState.value.longitude == 0.00) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.please_select_address_from_map,
                            _addAddressState.value.latitude
                        )
                    )
                )
            }
        }
        else {
            viewModelScope.launch {
                if (addAddressState.value.id == 0){
                    _addAddressState.value = addAddressState.value.copy(
                        response = addMyAddressUseCase.execute(
                            addAddressState.value.title,
                            addAddressState.value.address,
                            "${addAddressState.value.latitude},${addAddressState.value.longitude}"
                        )
                    )
                }
                else{
                    _addAddressState.value = addAddressState.value.copy(
                        response = updateMyAddressUseCase.execute(
                            addAddressState.value.id.toString(),
                            addAddressState.value.title,
                            addAddressState.value.address,
                            "${addAddressState.value.latitude},${addAddressState.value.longitude}"
                        )
                    )
                }
            }
        }
    }
}
