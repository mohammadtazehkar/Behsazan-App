package com.example.behsaz.ui.screens

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carto.styles.MarkerStyleBuilder
import com.carto.utils.BitmapUtils
import com.example.behsaz.R
import com.example.behsaz.presentation.events.MapEvent
import com.example.behsaz.presentation.events.MapUIEvent
import com.example.behsaz.presentation.viewmodels.MapViewModel
import com.example.behsaz.presentation.viewmodels.MapViewModelFactory
import com.example.behsaz.presentation.viewmodels.SharedViewModel
import com.example.behsaz.ui.components.AppSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.SecondaryButton
import com.example.behsaz.utils.ClickHelper
import com.example.behsaz.utils.Constants.FOR_VIEW_LOCATION
import com.example.behsaz.utils.UIText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.model.Marker
import org.neshan.mapsdk.style.NeshanMapStyle

private var color : Int = 0
@Composable
fun MapScreen(
    forWhat : String,
    mapViewModel: MapViewModel = viewModel(factory = MapViewModelFactory(LocalContext.current,forWhat)),
    sharedViewModel: SharedViewModel,
    onSubmitLocation: () -> Unit,
    onNavUp: () -> Unit
) {
    val context = LocalContext.current
    val mapState = mapViewModel.mapState.value
    val sharedState = sharedViewModel.sharedState.value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val map = remember {
        MapView(context).apply {
//            clipToOutline = true
        }
    }

    val locationRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                //If the user clicks OK to turn on location
                mapViewModel.onEvent(MapEvent.UpdateGPSLocationStatus(true))

            } else {
                mapViewModel.onEvent(MapEvent.UpdateGPSLocationStatus(false))
                if (!mapState.isMobileGPSLocationEnable) {//If the user cancels, Still make a check and then exit the activity
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = UIText.StringResource(R.string.turn_on_gps).asString(context)
                        )
                    }
                }
            }
        })
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions.isNotEmpty()) {
                val permissionsGranted = permissions.values.all { it }
                if (!mapState.isMobileGPSLocationEnable) {
                    mapViewModel.onEvent(MapEvent.EnableLocationRequest(makeRequest = {
                        locationRequestLauncher.launch(it)//Launch it to show the prompt.
                    }))
                }
                mapViewModel.onEvent(MapEvent.UpdatePermissionStatus(permissionsGranted))
            }
        })

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            //Permission is not Granted
            if (event == Lifecycle.Event.ON_START && !mapState.isLocationPermissionGranted) {
                locationPermissionLauncher.launch(mapState.permissionList)
            }
            //Permission Granted && GPSLocation is Disable
            else if (event == Lifecycle.Event.ON_START && mapState.isLocationPermissionGranted && !mapState.isMobileGPSLocationEnable) {
                mapViewModel.onEvent(MapEvent.EnableLocationRequest(makeRequest = {
                    locationRequestLauncher.launch(it)//Launch it to show the prompt.
                }))
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
    DisposableEffect(key1 = mapState.locationProvider) {
        if (mapViewModel.mapState.value.forWhat != FOR_VIEW_LOCATION) {
            mapViewModel.onEvent(MapEvent.InitLocation)
        }else{
            showSelectedLocationMarker(map,context,sharedState.latitude!!,sharedState.longitude!!)
        }
        onDispose {
            mapViewModel.onEvent(MapEvent.StopLocationUpdate)
        }
    }

    LaunchedEffect(key1 = true) {
        mapViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is MapUIEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                is MapUIEvent.LaunchPermissionLauncher ->{
                    locationPermissionLauncher.launch(mapState.permissionList)
                }
                is MapUIEvent.MoveCameraToTarget ->{
                    map.moveCamera(LatLng(event.location.latitude, event.location.longitude), .5f)
                    map.showAccuracyCircle(event.location)
                    map.setZoom(18f, .5f)

                }
                is MapUIEvent.AddMarkerToMap ->{
                    map.addMarker(event.marker)
                    map.enableUserMarkerRotation(event.marker)
                }
                is MapUIEvent.RemoveMarkerFromMap ->{
                    map.removeMarker(event.marker)
                }
            }
        }
    }

    color = MaterialTheme.colorScheme.primary.toArgb()
    LaunchedEffect(key1 = mapState.markerColor == 0){
        mapViewModel.onEvent(MapEvent.UpdateMarkerColor(color))
    }

    Scaffold(
        topBar = {
            AppTopAppBar(title = stringResource(id = R.string.map), isBackVisible = true, onBack = onNavUp)
        },
        floatingActionButton = {
            if (mapState.actionButtonVisible) {
                FloatingActionButton(
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape,
                    onClick = { ClickHelper.getInstance().clickOnce {
                        if (!mapState.isLocationPermissionGranted) {
                            locationPermissionLauncher.launch(mapState.permissionList)
                        } else if (!mapState.isMobileGPSLocationEnable) {
                            mapViewModel.onEvent(MapEvent.EnableLocationRequest(makeRequest = {
                                locationRequestLauncher.launch(it)//Launch it to show the prompt.
                            }))
                        } else {
                            map.moveCamera(
                                LatLng(
                                    mapState.currentLatitude,
                                    mapState.currentLongitude
                                ), .5f
                            )
                            map.setZoom(18f, .5f)
                        }
                    }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                )
                {
                    Image(
                        painterResource(id = R.mipmap.ic_current_location_white),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "icon"
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                AppSnackBar(it)
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()){
            AndroidView(
                { map }
            ) { map ->
                map.mapStyle = NeshanMapStyle.STYLE_4
                map.settings.isMapRotationEnabled = false
//            map.isTrafficEnabled = true
            }
            if (mapState.actionButtonVisible) {
                Image(
                    painterResource(id = R.mipmap.ic_marker_blue),
                    modifier = Modifier
                        .size(128.dp)
                        .align(Alignment.Center)
                        .padding(bottom = 64.dp),
                    contentDescription = "icon"
                )
                SecondaryButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 64.dp),
                    stringId = R.string.submit_address,
                    onClick = {
                        sharedViewModel.selectLocation(map.cameraTargetPosition.latitude,map.cameraTargetPosition.longitude)
                        onSubmitLocation()
                        Log.i(
                            "mamali",
                            "selected location : 'latitude ${map.cameraTargetPosition.latitude}' and 'longitude : ${map.cameraTargetPosition.longitude}'"
                        )

                    }
                )
            }
        }

    }
}
private fun showSelectedLocationMarker(map: MapView,context: Context, latitude: Double, longitude: Double) {
    val markStCr = MarkerStyleBuilder()
    markStCr.size = 70f
    markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
        BitmapFactory.decodeResource(
            context.resources, R.mipmap.ic_marker_blue
        )
    )
    val markSt = markStCr.buildStyle()
    val marker = Marker(LatLng(latitude, longitude), markSt)
    // Adding marker to map!
    map.addMarker(marker)
    map.moveCamera(LatLng(latitude, longitude), .5f)
    map.setZoom(18f, .5f)
}