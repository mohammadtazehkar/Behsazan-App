package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.myService.APIGetSubCategoryListResponse
import com.example.behsaz.domain.repository.MyServiceListRepository
import com.example.behsaz.utils.Resource

class GetSubCategoryListUseCase(private val myServiceListRepository: MyServiceListRepository) {
    suspend fun execute(categoryId: String): Resource<APIGetSubCategoryListResponse> = myServiceListRepository.getSubCategoryList(categoryId)
}