package com.example.behsaz.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.behsaz.presentation.events.MapEvent
import com.example.behsaz.presentation.states.SharedState

class SharedViewModel : ViewModel() {
    private val _sharedState = mutableStateOf(
        SharedState(
            latitude = 0.00 ,
            longitude = 0.00
        )
    )
    val sharedState: State<SharedState> = _sharedState

    fun selectLocation(latitude: Double , longitude: Double){
        _sharedState.value = sharedState.value.copy(
            latitude = latitude,
            longitude = longitude
        )
    }
}
