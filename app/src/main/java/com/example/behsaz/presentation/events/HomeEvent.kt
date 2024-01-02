package com.example.behsaz.presentation.events

sealed class HomeEvent{
    data object UpdateLogoutDialog : HomeEvent()
    data object PrepareData : HomeEvent()
    data class UpdateLoading(val status: Boolean) : HomeEvent()
    data object GetHomeData : HomeEvent()
    data class DoLogout(val onLogoutComplete: () -> Unit) : HomeEvent()

}
