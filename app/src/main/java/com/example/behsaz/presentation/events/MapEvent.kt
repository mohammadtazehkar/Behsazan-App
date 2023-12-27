package com.example.behsaz.presentation.events

import androidx.activity.result.IntentSenderRequest

sealed class MapEvent{
    data class UpdateGPSLocationStatus (val status: Boolean): MapEvent()
    data class UpdatePermissionStatus (val status: Boolean) : MapEvent()
    data class UpdateCurrentLatLong (val latitude: Double, val longitude: Double) : MapEvent()
    data class EnableLocationRequest (val makeRequest: (intentSenderRequest: IntentSenderRequest) -> Unit) : MapEvent()//Lambda to call when locations are off.
    object InitLocation : MapEvent()
    object StopLocationUpdate : MapEvent()
    data class UpdateMarkerColor (val color: Int): MapEvent()

}
