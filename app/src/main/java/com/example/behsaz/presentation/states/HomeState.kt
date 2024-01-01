package com.example.behsaz.presentation.states

import com.example.behsaz.data.models.home.APIHomeDataResponse
import com.example.behsaz.data.models.home.CategoryListData
import com.example.behsaz.data.models.home.SlideListData
import com.example.behsaz.utils.Resource

data class HomeState(
    var fullName : String = "",
    var isLoading : Boolean = true,
    var categoryListState : List<CategoryListData> = mutableListOf(),
//    var imageList : List<SlideListData> = mutableListOf(),
    var imageList : List<String> = mutableListOf(),
    var logoutDialogVisible : Boolean = false,
    var response: Resource<APIHomeDataResponse>


)
