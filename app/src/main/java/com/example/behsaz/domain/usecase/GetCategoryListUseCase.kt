package com.example.behsaz.domain.usecase

import com.example.behsaz.data.models.myService.APIGetCategoryListResponse
import com.example.behsaz.domain.repository.MyServiceListRepository
import com.example.behsaz.utils.Resource
import javax.inject.Inject

class GetCategoryListUseCase(private val myServiceListRepository: MyServiceListRepository) {
    suspend fun execute(categoryId: String): Resource<APIGetCategoryListResponse> = myServiceListRepository.getCategoryList(categoryId)
}