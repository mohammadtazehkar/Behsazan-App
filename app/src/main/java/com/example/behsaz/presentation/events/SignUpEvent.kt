package com.example.behsaz.presentation.events

sealed class SignUpEvent{
    data class  UpdatePersonalTextFieldState(var type:Int, val newValue: String): SignUpEvent()
    data class  UpdateUserTextFieldState(var type:Int, val newValue: String): SignUpEvent()
    data class SignUpClicked(val onSignUpCompleted: () -> Unit) : SignUpEvent()
}
