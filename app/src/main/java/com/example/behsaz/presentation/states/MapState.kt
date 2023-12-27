package com.example.behsaz.presentation.states

import com.google.android.gms.location.FusedLocationProviderClient
import org.neshan.mapsdk.model.Marker

data class MapState(
    var permissionList : Array<String>,
    var isLocationPermissionGranted : Boolean = false,
    var isMobileGPSLocationEnable : Boolean = false,
    var currentLatitude : Double = 0.00,
    var currentLongitude : Double = 0.00,
    var locationProvider : FusedLocationProviderClient ,
    var marker : Marker? = null,
    var markerColor : Int = 0,
    var actionButtonVisible : Boolean = false,
    var forWhat : String
)
