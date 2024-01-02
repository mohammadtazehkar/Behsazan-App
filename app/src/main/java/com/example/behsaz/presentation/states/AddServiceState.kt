package com.example.behsaz.presentation.states

import com.example.behsaz.data.models.home.CategoryListData
import com.example.behsaz.data.models.myAddress.APIMyAddressListResponse
import com.example.behsaz.data.models.myAddress.MyAddressListData
import com.example.behsaz.data.models.myService.APIAddServiceResponse
import com.example.behsaz.data.models.myService.APIGetSubCategoryListResponse
import com.example.behsaz.data.models.myService.SubCategoryListData
import com.example.behsaz.utils.Resource

data class AddServiceState(
    var isLoading: Boolean = false,
    var categoryId : Int = 0,
    var categoryTitle : String = "",
    var subCategoryId : Int = 0,
    var subCategoryTitle : String = "",
    var address: String = "",
    var latitude: Double = 0.00,
    var longitude: Double = 0.00,
    var myAddressId: Int = 0,
    var description: String = "",
    var myAddressListState : List<MyAddressListData> = mutableListOf(),
    var subCategoryListState : List<SubCategoryListData> = mutableListOf(),
    var myAddressListDialogVisible: Boolean = false,
    var subCategoryListDialogVisible: Boolean = false,
    var responseAddService: Resource<APIAddServiceResponse>,
    var responseMyAddressList: Resource<APIMyAddressListResponse>,
    var responseSubCategoryList: Resource<APIGetSubCategoryListResponse>
)
