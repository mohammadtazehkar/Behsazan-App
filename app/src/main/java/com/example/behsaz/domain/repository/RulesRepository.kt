package com.example.behsaz.domain.repository

import com.example.behsaz.data.models.rules.APIGetRulesResponse
import com.example.behsaz.utils.Resource

interface RulesRepository {

    suspend fun getRules(): Resource<APIGetRulesResponse>

}