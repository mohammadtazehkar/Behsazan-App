package com.example.behsaz.presentation.states

import com.example.behsaz.data.models.signup.APISignUpResponse
import com.example.behsaz.utils.Resource

data class SignUpState(
    var isLoading: Boolean = false,
    var personalTextFieldStates : MutableList<String> = mutableListOf(),
    var userTextFieldStates : MutableList<String> = mutableListOf(),
    var response : Resource<APISignUpResponse>

)
