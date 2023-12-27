package com.example.behsaz.presentation.states

import com.example.behsaz.data.models.home.CategoryListData
import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import com.example.behsaz.data.models.myAddress.MyAddressListData
import com.example.behsaz.data.models.myService.APIAddServiceResponse
import com.example.behsaz.data.models.myService.APIGetCategoryListResponse
import com.example.behsaz.utils.Resource

data class AddServiceState(
    var categoryId : Int = 0,
    var categoryTitle : String = "",
    var address: String = "",
    var latitude: Double = 0.00,
    var longitude: Double = 0.00,
    var myAddressId: Int = 0,
    var description: String = "",
    var myAddressListState : List<MyAddressListData> = mutableListOf(),
    var categoryListState : List<CategoryListData> = mutableListOf(),
    var myAddressListDialogVisible: Boolean = false,
    var categoryListDialogVisible: Boolean = false,
    var responseAddService: Resource<APIAddServiceResponse>,
    var responseMyAddressList: Resource<APIMyAddressListResponse>,
    var responseCategoryList: Resource<APIGetCategoryListResponse>
)
