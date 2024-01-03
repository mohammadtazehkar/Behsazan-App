package com.example.behsaz.presentation.events

sealed class AddAddressEvent{
    data class UpdateTitleTextFieldState(val newValue: String): AddAddressEvent()
    data class UpdateAddressTextFieldState(val newValue: String): AddAddressEvent()
    data object AddAddressClicked : AddAddressEvent()
    data class UpdateSelectedLocation(val latitude: Double, val longitude: Double): AddAddressEvent()
    data class UpdateLoading(val status: Boolean): AddAddressEvent()

}
