package com.example.behsaz.presentation.events

sealed class AddServiceEvent{
    data class SelectSubCategory(val subCategoryId: Int, val subCategoryTitle: String): AddServiceEvent()
    data class UpdateAddressTextFieldState(val newValue: String): AddServiceEvent()
    data class SelectMyAddress(val myAddressId: Int, val address: String, val latitude: Double, val longitude: Double,): AddServiceEvent()
    data class UpdateDescriptionTextFieldState(val newValue: String): AddServiceEvent()
    data class AddServiceClicked(val latitude: Double, val longitude: Double): AddServiceEvent()
    data object UpdateMyAddressListDialog : AddServiceEvent()
    data object UpdateSubCategoryListDialog : AddServiceEvent()
    data object GetMyAddressList : AddServiceEvent()
    data object GetSubCategoryList : AddServiceEvent()
    data object PrepareMyAddressList : AddServiceEvent()
    data object PrepareSubCategoryList : AddServiceEvent()
    data class UpdateLoading(val status: Boolean): AddServiceEvent()
}
