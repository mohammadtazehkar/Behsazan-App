package com.example.behsaz.presentation.states

import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import com.example.behsaz.data.models.myAddress.MyAddressListData
import com.example.behsaz.utils.Resource


data class MyAddressListState(
    var listState: List<MyAddressListData> = mutableListOf(),
    var fabVisible: Boolean = true,
    var response: Resource<APIMyAddressListResponse>
)
