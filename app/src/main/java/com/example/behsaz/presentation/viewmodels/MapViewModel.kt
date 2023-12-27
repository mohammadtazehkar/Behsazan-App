package com.example.behsaz.presentation.viewmodels

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.carto.graphics.Color
import com.carto.styles.MarkerStyleBuilder
import com.carto.utils.BitmapUtils
import com.example.behsaz.presentation.events.MapEvent
import com.example.behsaz.presentation.events.MapUIEvent
import com.example.behsaz.presentation.states.MapState
import com.example.behsaz.utils.Constants.FOR_ADD_LOCATION
import com.example.behsaz.utils.Constants.FOR_EDIT_LOCATION
import com.example.behsaz.utils.Constants.FOR_VIEW_LOCATION
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.model.Marker
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class MapViewModel(private var context: Context, forWhat : String ) : ViewModel(){
    private lateinit var locationCallback: LocationCallback
    private val _mapState = mutableStateOf(
        MapState(
            permissionList = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            isLocationPermissionGranted = false,
            isMobileGPSLocationEnable = false,
            currentLatitude = 0.00,
            currentLongitude = 0.00,
            locationProvider = LocationServices.getFusedLocationProviderClient(context),
            markerColor = 0,
            actionButtonVisible = forWhat == FOR_ADD_LOCATION || forWhat == FOR_EDIT_LOCATION,
            forWhat = forWhat
        )
    )
    val mapState: State<MapState> = _mapState

    private val _uiEventFlow = MutableSharedFlow<MapUIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        updateLocationServiceStatus()
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.UpdateGPSLocationStatus -> {
                _mapState.value = mapState.value.copy(
                    isMobileGPSLocationEnable = event.status
                )
                if (event.status){
                    initLocation()
                }
            }
            is MapEvent.UpdatePermissionStatus -> {
                _mapState.value = mapState.value.copy(
                    isLocationPermissionGranted = event.status
                )
            }
            is MapEvent.UpdateCurrentLatLong -> {
                _mapState.value = mapState.value.copy(
                    currentLatitude = event.latitude,
                    currentLongitude = event.longitude
                )
            }
            is MapEvent.EnableLocationRequest ->{
                enableLocationRequest(event.makeRequest)
            }
            is MapEvent.InitLocation -> {
                    initLocation()
            }
            is MapEvent.StopLocationUpdate -> {
                stopLocationUpdate()
            }
            is MapEvent.UpdateMarkerColor -> {
                _mapState.value = mapState.value.copy(
                    markerColor = event.color
                )
            }

            else -> {}
        }
    }


    private fun updateLocationServiceStatus() {
        _mapState.value = mapState.value.copy(
            isMobileGPSLocationEnable = isConnected()
        )
        _mapState.value = mapState.value.copy(
            isLocationPermissionGranted = isGranted()
        )
    }
    private fun enableLocationRequest(makeRequest: (intentSenderRequest: IntentSenderRequest) -> Unit//Lambda to call when locations are off.
    ) {
        val locationRequest = LocationRequest.Builder(//Create a location request object
            Priority.PRIORITY_HIGH_ACCURACY,//Self explanatory
            10000//Interval -> shorter the interval more frequent location updates
        ).build()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())//Checksettings with building a request
        task.addOnSuccessListener { locationSettingsResponse ->
            Log.d(
                "Location",
                "enableLocationRequest: LocationService Already Enabled"
            )
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()//Create the request prompt
                    makeRequest(intentSenderRequest)//Make the request from UI
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }


    private fun isConnected(): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    private fun isGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }


    private fun initLocation(){
        locationCallback = object : LocationCallback() {
            //1
            override fun onLocationResult(result: LocationResult) {
                if (result.lastLocation != null) {
                    addUserMarker(result.lastLocation!!)
                    // Update data class with location data
                    _mapState.value = mapState.value.copy(
                        currentLatitude = result.lastLocation!!.latitude,
                        currentLongitude = result.lastLocation!!.longitude
                    )
                    viewModelScope.launch {
                        _uiEventFlow.emit(
                            MapUIEvent.MoveCameraToTarget(result.lastLocation!!)
                        )
                    }
                }
            }
        }
        //2 checkPermission
        if (mapState.value.isLocationPermissionGranted) {
            locationUpdate()
        } else {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    MapUIEvent.LaunchPermissionLauncher
                )
            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun locationUpdate() {
       locationCallback.let {
            //An encapsulation of various parameters for requesting
            val locationRequest =
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(60))
                    .apply {
                        setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                        setWaitForAccurateLocation(true)
                    }.build()
            //use FusedLocationProviderClient to request location update
            if(isGranted()) {
                mapState.value.locationProvider.requestLocationUpdates(
                    locationRequest,
                    it,
                    Looper.getMainLooper()
                )
            }
        }
    }
    private fun stopLocationUpdate() {
        try {
            //Removes all location updates for the given callback.
            if (mapState.value.forWhat != FOR_VIEW_LOCATION) {
                val removeTask = mapState.value.locationProvider.removeLocationUpdates(
                    locationCallback
                )
                removeTask.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("mamali", "Location Callback removed.")
                    } else {
                        Log.d("mamali", "Failed to remove Location Callback.")
                    }
                }
            }
        } catch (se: SecurityException) {
            Log.e("mamali", "Failed to remove Location Callback.. $se")
        }
    }
    private fun addUserMarker( loc: Location) {
        //remove existing marker from map
        if (mapState.value.marker != null) {
            viewModelScope.launch {
                _uiEventFlow.emit(
                    MapUIEvent.RemoveMarkerFromMap(mapState.value.marker!!)
                )
            }
        }
        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        val markStCr = MarkerStyleBuilder()
        markStCr.size = 70f
        markStCr.color = Color(mapState.value.markerColor)
        markStCr.anchorPointX = 0f
        markStCr.anchorPointY = 0f
        markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
            BitmapFactory.decodeResource(
                context.resources, org.neshan.mapsdk.R.drawable.ic_user_loc_3
            )
        )
        val markSt = markStCr.buildStyle()
        // Creating user marker
        _mapState.value = mapState.value.copy(
            marker = Marker(LatLng(loc.latitude, loc.longitude), markSt)
        )
        // Adding user marker to map!
        viewModelScope.launch {
            _uiEventFlow.emit(
                MapUIEvent.AddMarkerToMap(mapState.value.marker!!)
            )
        }
    }
}

class MapViewModelFactory(private val context : Context,private val forWhat : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)){
            return MapViewModel(context, forWhat) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}