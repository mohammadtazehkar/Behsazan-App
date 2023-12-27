package com.example.behsaz.presentation.events

import android.location.Location
import com.example.behsaz.utils.UIText
import org.neshan.mapsdk.model.Marker

sealed class MapUIEvent{
    data class ShowMessage(val message: UIText): MapUIEvent()
    object LaunchPermissionLauncher: MapUIEvent()
    data class MoveCameraToTarget(val location: Location): MapUIEvent()
    data class AddMarkerToMap(val marker: Marker): MapUIEvent()
    data class RemoveMarkerFromMap(val marker: Marker): MapUIEvent()
}
