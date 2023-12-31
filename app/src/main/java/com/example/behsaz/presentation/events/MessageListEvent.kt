package com.example.behsaz.presentation.events

sealed class MessageListEvent{
    data class UpdateLoading(val isLoading: Boolean): MessageListEvent()
    data object GetListFromServer : MessageListEvent()
    data object PrepareList : MessageListEvent()
}
