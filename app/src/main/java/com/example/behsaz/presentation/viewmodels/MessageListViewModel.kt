package com.example.behsaz.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.behsaz.domain.usecase.GetMessagesListUseCase
import com.example.behsaz.presentation.events.MessageListEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.states.MessageListState
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class MessageListViewModel @Inject constructor (private val getMessagesListUseCase: GetMessagesListUseCase) : ViewModel() {

    private val _messageListState = mutableStateOf(
        MessageListState(
            response = Resource.Loading()
        )
    )
    val messageListState: State<MessageListState> = _messageListState
    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        getListFromServer()
    }

    fun onEvent(event: MessageListEvent){
        when(event){
            is MessageListEvent.PrepareList ->{
                prepareList()
            }
            is MessageListEvent.GetListFromServer ->{
                getListFromServer()
            }
            is MessageListEvent.UpdateLoading ->{
                _messageListState.value = messageListState.value.copy(
                    isLoading = event.isLoading,
                )
            }
        }
    }

    private fun getListFromServer(){
        viewModelScope.launch {
            _messageListState.value = messageListState.value.copy(
                response = getMessagesListUseCase.execute()
            )
        }
    }
    private fun prepareList(){
        _messageListState.value = messageListState.value.copy(
            listState = messageListState.value.response.data?.data!!,
        )
    }

}