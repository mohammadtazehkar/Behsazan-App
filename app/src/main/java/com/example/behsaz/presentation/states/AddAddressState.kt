package com.example.behsaz.presentation.states

import com.example.behsaz.data.models.myAddress.APIAddAddressResponse
import com.example.behsaz.utils.Resource

data class AddAddressState(
    var id: Int,
    var title: String,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var forWhat: String,
    var actionTitleId: Int,
    var response: Resource<APIAddAddressResponse>
)
