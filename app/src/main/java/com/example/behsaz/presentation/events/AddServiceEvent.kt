package com.example.behsaz.presentation.events

sealed class AddServiceEvent{
    data class SelectCategory(val categoryId: Int, val categoryTitle: String): AddServiceEvent()
    data class UpdateAddressTextFieldState(val newValue: String): AddServiceEvent()
    data class SelectMyAddress(val myAddressId: Int, val address: String, val latitude: Double, val longitude: Double,): AddServiceEvent()
    data class UpdateDescriptionTextFieldState(val newValue: String): AddServiceEvent()
    data class AddServiceClicked(val latitude: Double, val longitude: Double): AddServiceEvent()
    data object UpdateMyAddressListDialog : AddServiceEvent()
    data object UpdateCategoryListDialog : AddServiceEvent()
    data object GetMyAddressList : AddServiceEvent()
    data object GetCategoryList : AddServiceEvent()
    data object PrepareMyAddressList : AddServiceEvent()
    data object PrepareCategoryList : AddServiceEvent()
}
