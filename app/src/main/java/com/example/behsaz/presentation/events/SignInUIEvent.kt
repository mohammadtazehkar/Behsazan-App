package com.example.behsaz.presentation.events

import com.example.behsaz.utils.UIText

sealed class SignInUIEvent{
    data class ShowMessage(val message: UIText): SignInUIEvent()
    data class ExpiredToken(val message: UIText): SignInUIEvent()
}
