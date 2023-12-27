package com.example.behsaz.presentation.events

sealed class SignInEvent{
    data object KeyboardOpen : SignInEvent()
    data object KeyboardClose : SignInEvent()
    data class  UpdateTextFieldState(var type:Int, val newValue: String): SignInEvent()
    data class LoginClicked(val onSignInCompleted: () -> Unit) : SignInEvent()
}
