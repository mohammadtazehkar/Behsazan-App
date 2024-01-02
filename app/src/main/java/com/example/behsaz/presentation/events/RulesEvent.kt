package com.example.behsaz.presentation.events

sealed class RulesEvent{
    data object GetRulesFromServer : RulesEvent()
    data object PrepareRules : RulesEvent()

    data class UpdateLoading(val status: Boolean): RulesEvent()

}
