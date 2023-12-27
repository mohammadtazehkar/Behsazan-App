package com.example.behsaz.presentation.events

sealed class MyServiceListEvent{
    data class UpdateLoading(val isLoading: Boolean): MyServiceListEvent()
    data object GetListFromServer : MyServiceListEvent()
    data object PrepareList : MyServiceListEvent()
}
