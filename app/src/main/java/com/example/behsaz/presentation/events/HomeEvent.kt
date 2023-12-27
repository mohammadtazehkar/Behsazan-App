package com.example.behsaz.presentation.events

sealed class HomeEvent{
    data object UpdateLogoutDialog : HomeEvent()
    data object PrepareData : HomeEvent()
}
