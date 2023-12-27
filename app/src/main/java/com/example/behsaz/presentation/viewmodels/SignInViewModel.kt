package com.example.behsaz.presentation.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.behsaz.R
import com.example.behsaz.data.models.signin.APISignInResponse
import com.example.behsaz.data.models.signin.SignInCustomerData
import com.example.behsaz.data.models.signin.SignInData
import com.example.behsaz.data.models.signin.SignInTokenData
import com.example.behsaz.domain.usecase.SignInUseCase
import com.example.behsaz.utils.UIText
import com.example.behsaz.presentation.constants.AppKeyboard
import com.example.behsaz.presentation.constants.SignInInputTypes.PASSWORD
import com.example.behsaz.presentation.constants.SignInInputTypes.USERNAME
import com.example.behsaz.presentation.events.SignInEvent
import com.example.behsaz.presentation.states.SignInState
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.utils.JSonStatusCode.DUPLICATE_USERNAME
import com.example.behsaz.utils.JSonStatusCode.INVALID_USERNAME
import com.example.behsaz.utils.JSonStatusCode.SUCCESS
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

//@HiltViewModel
class SignInViewModel (private val signInUseCase: SignInUseCase) : ViewModel() {
    private val _signInState = mutableStateOf(
        SignInState(
            isBrandVisible = true,
            keyboardState = AppKeyboard.Closed,
            textFieldStates = mutableStateListOf("",""),
            response = Resource.Loading()
        )
    )
    val signInState: State<SignInState> = _signInState

    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {

            is SignInEvent.KeyboardOpen -> {
                _signInState.value = _signInState.value.copy(
                    keyboardState = AppKeyboard.Opened
                )
            }

            is SignInEvent.KeyboardClose -> {
                _signInState.value = _signInState.value.copy(
                    keyboardState = AppKeyboard.Closed
                )
            }

            is SignInEvent.UpdateTextFieldState -> {
                val newStateList = _signInState.value.textFieldStates
                newStateList[event.type] = event.newValue
                _signInState.value = _signInState.value.copy(
                    textFieldStates = newStateList
                )
            }

            is SignInEvent.LoginClicked -> {
                signIn(onSignInCompleted = event.onSignInCompleted)
            }
        }
    }

    private fun signIn(
        onSignInCompleted: () -> Unit
    ) {
        if (_signInState.value.textFieldStates[USERNAME].isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_username,
                            _signInState.value.textFieldStates[USERNAME]
                        )
                    )
                )
            }
        }
        else if (_signInState.value.textFieldStates[PASSWORD].isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_password,
                            _signInState.value.textFieldStates[PASSWORD]
                        )
                    )
                )
            }
        }
        else {
            //TODO getUserInfo and save and login
            viewModelScope.launch {
                _signInState.value = signInState.value.copy(
                    response = signInUseCase.execute(signInState.value.textFieldStates[USERNAME],signInState.value.textFieldStates[PASSWORD])
                )
                if (_signInState.value.response.data?.statusCode == INVALID_USERNAME){
                    _uiEventFlow.emit(
                        SignInUIEvent.ShowMessage(
                            message = UIText.StringResource(
                                resId = R.string.invalid_login_info,
                                _signInState.value.textFieldStates[PASSWORD]
                            )
                        )
                    )
                }else if (_signInState.value.response.data?.statusCode == SUCCESS){
                    onSignInCompleted()
                }
            }


//            _signInState.value = signInState.value.copy(
//                response = Resource.Loading()
//            )
//            try {
//                viewModelScope.launch{
//                    _signInState.value = signInState.value.copy(
//                        response = signInUseCase.execute()
//                    )
////                    val result = signInUseCase.execute()
////                    if (result.statusCode == SUCCESS){
////
////                    }else if (result.statusCode == INVALID_USERNAME){
////
////                    }
//                }
//            }catch (exception: Exception){
//                _signInState.value = signInState.value.copy(
//                    response = Resource.Error(exception.message.toString())
//                )
//            }

//            onSignInCompleted()
        }
    }
}

class SignInViewModelFactory(private val signInUseCase: SignInUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)){
            return SignInViewModel(signInUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}