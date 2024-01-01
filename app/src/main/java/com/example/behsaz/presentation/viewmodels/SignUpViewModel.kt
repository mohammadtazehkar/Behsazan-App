package com.example.behsaz.presentation.viewmodels

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.behsaz.R
import com.example.behsaz.domain.usecase.SignUpUseCase
import com.example.behsaz.presentation.constants.SignInInputTypes
import com.example.behsaz.utils.UIText
import com.example.behsaz.presentation.constants.SignUpInputTypes
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.events.SignUpEvent
import com.example.behsaz.presentation.states.SignUpState
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase): ViewModel() {
    private val _signUpState = mutableStateOf(
        SignUpState(
            personalTextFieldStates = mutableStateListOf(mutableStateOf(""), mutableStateOf(""), mutableStateOf(""), mutableStateOf("")),
            userTextFieldStates = mutableStateListOf(mutableStateOf(""), mutableStateOf(""), mutableStateOf(""), mutableStateOf("")),
            response = Resource.Error("")
        )
    )
    val signUpState: State<SignUpState> = _signUpState

    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()


    fun onEvent(event: SignUpEvent) {
        when (event) {

            is SignUpEvent.UpdatePersonalTextFieldState -> {
                val newStateList = _signUpState.value.personalTextFieldStates
                newStateList[event.type].value = event.newValue
                _signUpState.value = _signUpState.value.copy(
                    personalTextFieldStates = newStateList
                )
            }
            is SignUpEvent.UpdateUserTextFieldState -> {
                val newStateList = _signUpState.value.userTextFieldStates
                newStateList[event.type].value = event.newValue
                _signUpState.value = _signUpState.value.copy(
                    userTextFieldStates = newStateList
                )
            }
            is SignUpEvent.SignUpClicked -> {
                signUp(onSignUpCompleted = event.onSignUpCompleted)
            }
            is SignUpEvent.UpdateLoading -> {
                _signUpState.value = signUpState.value.copy(
                    isLoading = event.status
                )
            }
        }
    }

    private fun signUp(
        onSignUpCompleted: () -> Unit
    ) {
        if (_signUpState.value.personalTextFieldStates[SignUpInputTypes.FIRSTNAME].value.isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_first_name,
                            _signUpState.value.personalTextFieldStates[SignUpInputTypes.FIRSTNAME]
                        )
                    )
                )
            }
        }
        else if (_signUpState.value.personalTextFieldStates[SignUpInputTypes.LASTNAME].value.isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_last_name,
                            _signUpState.value.personalTextFieldStates[SignUpInputTypes.LASTNAME]
                        )
                    )
                )
            }
        }
        else if (_signUpState.value.personalTextFieldStates[SignUpInputTypes.MOBILE_NUMBER].value.isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_mobile_number,
                            _signUpState.value.personalTextFieldStates[SignUpInputTypes.MOBILE_NUMBER]
                        )
                    )
                )
            }
        }
        else if (_signUpState.value.personalTextFieldStates[SignUpInputTypes.MOBILE_NUMBER].value.length != 11) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.invalid_mobile_number,
                            _signUpState.value.personalTextFieldStates[SignUpInputTypes.MOBILE_NUMBER]
                        )
                    )
                )
            }
        }
        else if (_signUpState.value.personalTextFieldStates[SignUpInputTypes.MOBILE_NUMBER].value.substring(0,2) != "09") {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.invalid_mobile_number,
                            _signUpState.value.personalTextFieldStates[SignUpInputTypes.MOBILE_NUMBER]
                        )
                    )
                )
            }
        }
        else if (_signUpState.value.userTextFieldStates[SignUpInputTypes.USERNAME].value.isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_username,
                            _signUpState.value.userTextFieldStates[SignUpInputTypes.USERNAME]
                        )
                    )
                )
            }
        }
        else if (_signUpState.value.userTextFieldStates[SignUpInputTypes.PASSWORD].value.isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_password,
                            _signUpState.value.userTextFieldStates[SignUpInputTypes.PASSWORD]
                        )
                    )
                )
            }
        }
        else if (
            signUpState.value.userTextFieldStates[SignUpInputTypes.EMAIL].value.isNotEmpty() &&
            !Patterns.EMAIL_ADDRESS.matcher(_signUpState.value.userTextFieldStates[SignUpInputTypes.EMAIL].value).matches()
            ){
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.invalid_email,
                            _signUpState.value.userTextFieldStates[SignUpInputTypes.EMAIL]
                        )
                    )
                )
            }
        }
        else {
            _signUpState.value = signUpState.value.copy(
                response = Resource.Loading()
            )
            viewModelScope.launch {
                _signUpState.value = signUpState.value.copy(
                    response = signUpUseCase.execute(
                        signUpState.value.personalTextFieldStates[SignUpInputTypes.FIRSTNAME].value,
                        signUpState.value.personalTextFieldStates[SignUpInputTypes.LASTNAME].value,
                        signUpState.value.personalTextFieldStates[SignUpInputTypes.PHONE_NUMBER].value,
                        signUpState.value.personalTextFieldStates[SignUpInputTypes.MOBILE_NUMBER].value,
                        signUpState.value.userTextFieldStates[SignUpInputTypes.USERNAME].value,
                        signUpState.value.userTextFieldStates[SignUpInputTypes.PASSWORD].value,
                        signUpState.value.userTextFieldStates[SignUpInputTypes.EMAIL].value,
                        signUpState.value.userTextFieldStates[SignUpInputTypes.REAGENT_TOKEN].value
                    )
                )
                if (_signUpState.value.response.data?.statusCode == JSonStatusCode.DUPLICATE_USERNAME){
                    _uiEventFlow.emit(
                        SignInUIEvent.ShowMessage(
                            message = UIText.StringResource(
                                resId = R.string.duplicate_username,
                                _signUpState.value.userTextFieldStates[SignInInputTypes.PASSWORD]
                            )
                        )
                    )
                }else if (_signUpState.value.response.data?.statusCode == JSonStatusCode.SUCCESS){
                    onSignUpCompleted()
                }
            }
        }
    }
}