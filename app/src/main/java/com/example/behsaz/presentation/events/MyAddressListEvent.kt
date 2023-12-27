package com.example.behsaz.presentation.events

sealed class MyAddressListEvent{
    data object MakeFabVisible : MyAddressListEvent()
    data object MakeFabInVisible : MyAddressListEvent()
    data object PrepareList : MyAddressListEvent()
}
