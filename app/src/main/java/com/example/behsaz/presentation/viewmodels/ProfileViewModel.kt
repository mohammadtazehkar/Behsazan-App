package com.example.behsaz.presentation.viewmodels

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.behsaz.R
import com.example.behsaz.domain.usecase.GetProfileDataUseCase
import com.example.behsaz.domain.usecase.UpdateProfileDataUseCase
import com.example.behsaz.utils.UIText
import com.example.behsaz.presentation.constants.SignUpInputTypes
import com.example.behsaz.presentation.constants.SignUpInputTypes.EMAIL
import com.example.behsaz.presentation.constants.SignUpInputTypes.FIRSTNAME
import com.example.behsaz.presentation.constants.SignUpInputTypes.LASTNAME
import com.example.behsaz.presentation.constants.SignUpInputTypes.MOBILE_NUMBER
import com.example.behsaz.presentation.constants.SignUpInputTypes.PASSWORD
import com.example.behsaz.presentation.constants.SignUpInputTypes.PHONE_NUMBER
import com.example.behsaz.presentation.constants.SignUpInputTypes.REAGENT_TOKEN
import com.example.behsaz.presentation.constants.SignUpInputTypes.USERNAME
import com.example.behsaz.presentation.events.ProfileEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.states.ProfileState
import com.example.behsaz.utils.JSonStatusCode.DUPLICATE_USERNAME
import com.example.behsaz.utils.JSonStatusCode.SUCCESS
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class ProfileViewModel(
    private val getProfileUseCase: GetProfileDataUseCase,
    private val updateProfileUseCase: UpdateProfileDataUseCase
) : ViewModel() {
    private val _profileState = mutableStateOf(
        ProfileState(
            isEditable = false,
            personalTextFieldStates = mutableStateListOf("","","",""),
            userTextFieldStates = mutableStateListOf("","","",""),
            getProfileResponse = Resource.Loading(),
            updateProfileResponse = Resource.Loading()
        )
    )
    val profileState: State<ProfileState> = _profileState

    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            _profileState.value = profileState.value.copy(
                getProfileResponse = getProfileUseCase.execute()
            )
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.ChangeEditState -> {
                _profileState.value = _profileState.value.copy(
                    isEditable = !profileState.value.isEditable
                )
            }
            is ProfileEvent.UpdatePersonalTextFieldState -> {
                val newStateList = _profileState.value.personalTextFieldStates
                newStateList[event.type] = event.newValue
                _profileState.value = _profileState.value.copy(
                    personalTextFieldStates = newStateList
                )
            }
            is ProfileEvent.UpdateUserTextFieldState -> {
                val newStateList = _profileState.value.userTextFieldStates
                newStateList[event.type] = event.newValue
                _profileState.value = _profileState.value.copy(
                    userTextFieldStates = newStateList
                )
            }
            is ProfileEvent.UpdateClicked -> {
                update(onSignUpCompleted = event.onSignUpCompleted)
            }
            is ProfileEvent.SetRemoteProfileData -> {
                val personalStateList = _profileState.value.personalTextFieldStates
                personalStateList[FIRSTNAME] = profileState.value.getProfileResponse.data?.data?.firstName!!
                personalStateList[LASTNAME] = profileState.value.getProfileResponse.data?.data?.lastName!!
                personalStateList[PHONE_NUMBER] = profileState.value.getProfileResponse.data?.data?.phoneNumber!!
                personalStateList[MOBILE_NUMBER] = profileState.value.getProfileResponse.data?.data?.mobileNumber!!
                val userStateList = _profileState.value.personalTextFieldStates
                userStateList[USERNAME] = profileState.value.getProfileResponse.data?.data?.username!!
                userStateList[PASSWORD] = profileState.value.getProfileResponse.data?.data?.password!!
                userStateList[EMAIL] = profileState.value.getProfileResponse.data?.data?.email!!
                userStateList[REAGENT_TOKEN] = profileState.value.getProfileResponse.data?.data?.reagentToken!!
                _profileState.value = profileState.value.copy(
                    personalTextFieldStates = personalStateList,
                    userTextFieldStates = userStateList
                )
            }
        }
    }

    private fun update(
        onSignUpCompleted: () -> Unit
    ) {
        if (_profileState.value.personalTextFieldStates[FIRSTNAME].isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_first_name,
                            _profileState.value.personalTextFieldStates[SignUpInputTypes.FIRSTNAME]
                        )
                    )
                )
            }
        }
        else if (_profileState.value.personalTextFieldStates[LASTNAME].isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_last_name,
                            _profileState.value.personalTextFieldStates[SignUpInputTypes.LASTNAME]
                        )
                    )
                )
            }
        }
        else if (_profileState.value.personalTextFieldStates[MOBILE_NUMBER].isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_mobile_number,
                            _profileState.value.personalTextFieldStates[SignUpInputTypes.MOBILE_NUMBER]
                        )
                    )
                )
            }
        }
        else if (_profileState.value.personalTextFieldStates[MOBILE_NUMBER].length != 11) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.invalid_mobile_number,
                            _profileState.value.personalTextFieldStates[SignUpInputTypes.MOBILE_NUMBER]
                        )
                    )
                )
            }
        }
        else if (_profileState.value.personalTextFieldStates[MOBILE_NUMBER].substring(0,2) != "09") {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.invalid_mobile_number,
                            _profileState.value.personalTextFieldStates[SignUpInputTypes.MOBILE_NUMBER]
                        )
                    )
                )
            }
        }
        else if (_profileState.value.userTextFieldStates[USERNAME].isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_username,
                            _profileState.value.userTextFieldStates[SignUpInputTypes.USERNAME]
                        )
                    )
                )
            }
        }
        else if (_profileState.value.userTextFieldStates[PASSWORD].isEmpty()) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.empty_password,
                            _profileState.value.userTextFieldStates[SignUpInputTypes.PASSWORD]
                        )
                    )
                )
            }
        }
        else if (
            _profileState.value.userTextFieldStates[SignUpInputTypes.EMAIL].isNotEmpty() &&
            !Patterns.EMAIL_ADDRESS.matcher(_profileState.value.userTextFieldStates[SignUpInputTypes.EMAIL]).matches()
            ){
            viewModelScope.launch {
                _uiEventFlow.emit(
                    SignInUIEvent.ShowMessage(
                        message = UIText.StringResource(
                            resId = R.string.invalid_email,
                            _profileState.value.userTextFieldStates[SignUpInputTypes.EMAIL]
                        )
                    )
                )
            }
        }
        else {
            //TODO saveUserInfo and signUp
            viewModelScope.launch {
                _profileState.value = profileState.value.copy(
                    updateProfileResponse = updateProfileUseCase.execute(
                        profileState.value.personalTextFieldStates[FIRSTNAME],
                        profileState.value.personalTextFieldStates[LASTNAME],
                        profileState.value.personalTextFieldStates[PHONE_NUMBER],
                        profileState.value.personalTextFieldStates[MOBILE_NUMBER],
                        profileState.value.userTextFieldStates[USERNAME],
                        profileState.value.userTextFieldStates[PASSWORD],
                        profileState.value.userTextFieldStates[EMAIL]
                    )
                )
                if (_profileState.value.updateProfileResponse.data?.statusCode == DUPLICATE_USERNAME){
                    _uiEventFlow.emit(
                        SignInUIEvent.ShowMessage(
                            message = UIText.StringResource(
                                resId = R.string.duplicate_username,
                                _profileState.value.userTextFieldStates[SignUpInputTypes.USERNAME]
                            )
                        )
                    )
                }else if (_profileState.value.updateProfileResponse.data?.statusCode == SUCCESS){
                    onSignUpCompleted()
                }
            }
        }
    }
}

class ProfileViewModelFactory(
    private val getProfileUseCase: GetProfileDataUseCase,
    private val updateProfileUseCase: UpdateProfileDataUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(getProfileUseCase,updateProfileUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}