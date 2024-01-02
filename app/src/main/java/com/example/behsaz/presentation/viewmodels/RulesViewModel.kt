package com.example.behsaz.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.behsaz.domain.usecase.GetMessagesListUseCase
import com.example.behsaz.domain.usecase.GetRulesUseCase
import com.example.behsaz.presentation.events.ProfileEvent
import com.example.behsaz.presentation.events.RulesEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.states.MessageListState
import com.example.behsaz.presentation.states.RulesState
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class RulesViewModel @Inject constructor(private val getRulesUseCase: GetRulesUseCase) : ViewModel() {


    private val _rulesState = mutableStateOf(
        RulesState(
            url = "",
            response = Resource.Loading()
        )
    )
    val rulesState: State<RulesState> = _rulesState

    init {
        getRules()
    }

    fun onEvent(event: RulesEvent){
        when(event){
            is RulesEvent.GetRulesFromServer -> {
                getRules()
            }
            is RulesEvent.PrepareRules -> {
                prepareRules()
            }
            is RulesEvent.UpdateLoading -> {
                _rulesState.value = rulesState.value.copy(
                    isLoading = event.status
                )
            }

        }
    }

    private fun getRules(){
        viewModelScope.launch {
            _rulesState.value = rulesState.value.copy(
                response = getRulesUseCase.execute()
            )
        }
    }
    private fun prepareRules(){
        _rulesState.value = rulesState.value.copy(
            url = rulesState.value.response.data?.data!!,
        )
    }

}