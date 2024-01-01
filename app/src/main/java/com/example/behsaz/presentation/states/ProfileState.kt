package com.example.behsaz.presentation.states

import androidx.compose.runtime.MutableState
import com.example.behsaz.data.models.profile.APIProfileResponse
import com.example.behsaz.data.models.profile.APIUpdateProfileResponse
import com.example.behsaz.utils.Resource

data class ProfileState(
    var isLoading: Boolean = true,
    var personalTextFieldStates : MutableList<MutableState<String>> = mutableListOf(),
    var userTextFieldStates : MutableList<MutableState<String>> = mutableListOf(),
    var isEditable: Boolean = false,
    var getProfileResponse : Resource<APIProfileResponse>,
    var updateProfileResponse : Resource<APIUpdateProfileResponse>,

    )
