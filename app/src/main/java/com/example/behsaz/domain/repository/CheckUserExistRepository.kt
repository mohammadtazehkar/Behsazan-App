package com.example.behsaz.domain.repository

interface CheckUserExistRepository {

    suspend fun check(): Boolean

}