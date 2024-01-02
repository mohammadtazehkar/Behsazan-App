package com.example.behsaz.presentation.events

sealed class ProfileEvent{
    data object ChangeEditState : ProfileEvent()
    data class UpdatePersonalTextFieldState(var type:Int, val newValue: String): ProfileEvent()
    data class UpdateUserTextFieldState(var type:Int, val newValue: String): ProfileEvent()
    data class UpdateClicked(val onUpdateCompleted: () -> Unit) : ProfileEvent()
    data class UpdateLoading(var status: Boolean): ProfileEvent()
    data object GetProfileData : ProfileEvent()
    data class PrepareData(val fromUpdate: Boolean) : ProfileEvent()

}
