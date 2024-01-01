package com.example.behsaz.presentation.states

import androidx.compose.runtime.MutableState
import com.example.behsaz.data.models.signup.APISignUpResponse
import com.example.behsaz.utils.Resource

data class SignUpState(
    var isLoading: Boolean = false,
    var personalTextFieldStates : MutableList<MutableState<String>> = mutableListOf(),
    var userTextFieldStates : MutableList<MutableState<String>> = mutableListOf(),
    var response : Resource<APISignUpResponse>

)
