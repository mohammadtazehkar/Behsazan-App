package com.example.behsaz.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.behsaz.domain.usecase.GetMyServiceListUseCase
import com.example.behsaz.presentation.events.MyServiceListEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.states.MyServiceListState
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

//@HiltViewModel
class MyServiceListViewModel(private val getMyServiceListUseCase: GetMyServiceListUseCase): ViewModel() {

    private val _myServiceListState = mutableStateOf(
        MyServiceListState(
            isLoading = true,
            response = Resource.Loading()
        )
    )
    val myServiceListState: State<MyServiceListState> = _myServiceListState

    init {
        getListFromServer()
//        viewModelScope.launch {
//            _myServiceListState.value = myServiceListState.value.copy(
//                response = getMyServiceListUseCase.execute()
//            )
//        }
    }

    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: MyServiceListEvent){
        when(event){
            is MyServiceListEvent.PrepareList ->{
                prepareList()
            }
            is MyServiceListEvent.GetListFromServer ->{
                getListFromServer()
            }
            is MyServiceListEvent.UpdateLoading ->{
                _myServiceListState.value = myServiceListState.value.copy(
                    isLoading = event.isLoading,
                )
            }
        }
    }

    private fun getListFromServer(){
        viewModelScope.launch {
            _myServiceListState.value = myServiceListState.value.copy(
                response = getMyServiceListUseCase.execute()
            )
        }
    }

    private fun prepareList(){
        _myServiceListState.value = myServiceListState.value.copy(
            listState = myServiceListState.value.response.data?.data!!,
        )
    }

}

class MyServiceListViewModelFactory(private val getMyServiceListUseCase: GetMyServiceListUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyServiceListViewModel::class.java)){
            return MyServiceListViewModel(getMyServiceListUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}