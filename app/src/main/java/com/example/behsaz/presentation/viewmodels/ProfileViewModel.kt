package com.example.behsaz.presentation.viewmodels

import android.util.Log
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
import com.example.behsaz.presentation.constants.SignInInputTypes
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
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.JSonStatusCode.DUPLICATE_USERNAME
import com.example.behsaz.utils.JSonStatusCode.SUCCESS
import com.example.behsaz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileDataUseCase,
    private val updateProfileUseCase: UpdateProfileDataUseCase
) : ViewModel() {
    private val _profileState = mutableStateOf(
        ProfileState(
            isEditable = false,
            personalTextFieldStates = mutableStateListOf(mutableStateOf(""), mutableStateOf(""), mutableStateOf(""), mutableStateOf("")),
            userTextFieldStates = mutableStateListOf(mutableStateOf(""), mutableStateOf(""), mutableStateOf(""), mutableStateOf("")),
            getProfileResponse = Resource.Loading(),
            updateProfileResponse = Resource.Error("")
        )
    )
    val profileState: State<ProfileState> = _profileState

    private val _uiEventFlow = MutableSharedFlow<SignInUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        getProfileData()
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
                newStateList[event.type].value = event.newValue
                _profileState.value = _profileState.value.copy(
                    personalTextFieldStates = newStateList
                )
            }

            is ProfileEvent.UpdateUserTextFieldState -> {
                val newStateList = _profileState.value.userTextFieldStates
                newStateList[event.type].value = event.newValue
                _profileState.value = _profileState.value.copy(
                    userTextFieldStates = newStateList
                )
            }

            is ProfileEvent.UpdateClicked -> {
                update(onUpdateCompleted = event.onUpdateCompleted)
            }

            is ProfileEvent.UpdateLoading -> {
                _profileState.value = profileState.value.copy(
                    isLoading = event.status
                )
            }

            is ProfileEvent.PrepareData -> {
                prepareData(event.fromUpdate)
            }

        }
    }

    private fun getProfileData() {
        viewModelScope.launch {
            _profileState.value = profileState.value.copy(
                getProfileResponse = getProfileUseCase.execute()
            )
            if (profileState.value.getProfileResponse.data?.statusCode == JSonStatusCode.EXPIRED_TOKEN){
                _uiEventFlow.emit(
                    SignInUIEvent.ExpiredToken(
                        message = UIText.StringResource(
                            resId = R.string.expired_token,
                            profileState.value.userTextFieldStates[SignInInputTypes.PASSWORD]
                        )
                    )
                )
            }
        }

    }

    private fun prepareData(fromUpdate: Boolean) {
        val personalStateList = profileState.value.personalTextFieldStates
        val userStateList = profileState.value.userTextFieldStates

        if (fromUpdate){
            personalStateList[FIRSTNAME].value = profileState.value.updateProfileResponse.data?.data?.customer!!.firstName
            personalStateList[LASTNAME].value = profileState.value.updateProfileResponse.data?.data?.customer!!.lastName
            personalStateList[PHONE_NUMBER].value = profileState.value.updateProfileResponse.data?.data?.customer!!.phoneNumber
            personalStateList[MOBILE_NUMBER].value = profileState.value.updateProfileResponse.data?.data?.customer!!.mobileNumber
            userStateList[USERNAME].value = profileState.value.updateProfileResponse.data?.data?.customer!!.username
            userStateList[PASSWORD].value = profileState.value.updateProfileResponse.data?.data?.customer!!.password
            userStateList[EMAIL].value = profileState.value.updateProfileResponse.data?.data?.customer!!.email
            userStateList[REAGENT_TOKEN].value = profileState.value.updateProfileResponse.data?.data?.customer!!.reagentToken
        }else {
            personalStateList[FIRSTNAME].value = profileState.value.getProfileResponse.data?.data!!.firstName
            personalStateList[LASTNAME].value = profileState.value.getProfileResponse.data?.data!!.lastName
            personalStateList[PHONE_NUMBER].value = profileState.value.getProfileResponse.data?.data!!.phoneNumber
            personalStateList[MOBILE_NUMBER].value = profileState.value.getProfileResponse.data?.data!!.mobileNumber
            userStateList[USERNAME].value = profileState.value.getProfileResponse.data?.data!!.username
            userStateList[PASSWORD].value = profileState.value.getProfileResponse.data?.data!!.password
            userStateList[EMAIL].value = profileState.value.getProfileResponse.data?.data!!.email
            userStateList[REAGENT_TOKEN].value = profileState.value.getProfileResponse.data?.data!!.reagentToken
        }

        _profileState.value = profileState.value.copy(
            personalTextFieldStates = personalStateList,
            userTextFieldStates = userStateList
        )
    }

    private fun update(
        onUpdateCompleted: () -> Unit
    ) {
        if (_profileState.value.personalTextFieldStates[FIRSTNAME].value.isEmpty()) {
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
        else if (_profileState.value.personalTextFieldStates[LASTNAME].value.isEmpty()) {
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
        else if (_profileState.value.personalTextFieldStates[MOBILE_NUMBER].value.isEmpty()) {
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
        else if (_profileState.value.personalTextFieldStates[MOBILE_NUMBER].value.length != 11) {
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
        else if (_profileState.value.personalTextFieldStates[MOBILE_NUMBER].value.substring(0, 2) != "09"
        ) {
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
        else if (_profileState.value.userTextFieldStates[USERNAME].value.isEmpty()) {
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
        else if (_profileState.value.userTextFieldStates[PASSWORD].value.isEmpty()) {
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
            _profileState.value.userTextFieldStates[EMAIL].value.isNotEmpty() &&
            !Patterns.EMAIL_ADDRESS.matcher(_profileState.value.userTextFieldStates[EMAIL].value)
                .matches()
        ) {
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
            _profileState.value = profileState.value.copy(
                updateProfileResponse = Resource.Loading()
            )
            viewModelScope.launch {
                _profileState.value = profileState.value.copy(
                    updateProfileResponse = updateProfileUseCase.execute(
                        profileState.value.personalTextFieldStates[FIRSTNAME].value,
                        profileState.value.personalTextFieldStates[LASTNAME].value,
                        profileState.value.personalTextFieldStates[PHONE_NUMBER].value,
                        profileState.value.personalTextFieldStates[MOBILE_NUMBER].value,
                        profileState.value.userTextFieldStates[USERNAME].value,
                        profileState.value.userTextFieldStates[PASSWORD].value,
                        profileState.value.userTextFieldStates[EMAIL].value
                    )
                )
                if (_profileState.value.updateProfileResponse.data?.statusCode == DUPLICATE_USERNAME) {
                    _uiEventFlow.emit(
                        SignInUIEvent.ShowMessage(
                            message = UIText.StringResource(
                                resId = R.string.duplicate_username,
                                _profileState.value.userTextFieldStates[SignUpInputTypes.USERNAME]
                            )
                        )
                    )
                } else if (_profileState.value.updateProfileResponse.data?.statusCode == SUCCESS) {
                    onUpdateCompleted()
                }
            }
        }
    }
}