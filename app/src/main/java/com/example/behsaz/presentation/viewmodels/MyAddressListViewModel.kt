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

@HiltViewModel
class MyAddressListViewModel @Inject constructor (private val getMyAddressListUseCase: GetMyAddressListUseCase): ViewModel() {

    private val _myAddressListState = mutableStateOf(
        MyAddressListState(
            fabVisible = true,
            response = Resource.Loading()
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
            is MyAddressListEvent.UpdateLoading -> {
                _myAddressListState.value = myAddressListState.value.copy(
                    isLoading = event.status
                )
            }

        }
    }
}