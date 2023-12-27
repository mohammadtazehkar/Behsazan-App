package com.example.behsaz.presentation.states

import com.example.behsaz.data.models.signin.APISignInResponse
import com.example.behsaz.presentation.constants.AppKeyboard
import com.example.behsaz.utils.Resource

data class SignInState(
    var isBrandVisible: Boolean = true,
    var keyboardState: AppKeyboard = AppKeyboard.Closed,
    var textFieldStates : MutableList<String> = mutableListOf(),
    var response : Resource<APISignInResponse>
)
