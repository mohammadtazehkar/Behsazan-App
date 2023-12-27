package com.example.behsaz.presentation.states

import com.example.behsaz.data.models.myService.APIMyServiceListResponse
import com.example.behsaz.data.models.myService.MyServiceListData
import com.example.behsaz.utils.Resource

data class MyServiceListState(
    var listState : List<MyServiceListData> = mutableListOf(),
    var isLoading : Boolean = true,
    var response : Resource<APIMyServiceListResponse>,
)
