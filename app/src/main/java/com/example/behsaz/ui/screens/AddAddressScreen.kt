package com.example.behsaz.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.behsaz.R
import com.example.behsaz.presentation.events.AddAddressEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.viewmodels.AddAddressViewModel
import com.example.behsaz.presentation.viewmodels.SharedViewModel
import com.example.behsaz.ui.components.AppSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.CardColumnMediumCorner
import com.example.behsaz.ui.components.CardRowMediumCorner
import com.example.behsaz.ui.components.SecondaryButton
import com.example.behsaz.ui.components.TextBodyMedium
import com.example.behsaz.ui.components.TextTitleSmall
import com.example.behsaz.utils.Constants
import com.example.behsaz.utils.Constants.FOR_ADD
import com.example.behsaz.utils.Constants.FOR_ADD_LOCATION
import com.example.behsaz.utils.Constants.FOR_EDIT_LOCATION
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.UIText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddAddressScreen(
    sharedViewModel: SharedViewModel,
    addAddressViewModel: AddAddressViewModel = hiltViewModel(),
    onSelectLocation: (String) -> Unit,
    onSuccess: () -> Unit,
    onExpiredToken: () -> Unit,
    onNavUp: () -> Unit
) {
    val context = LocalContext.current
    val addAddressState = addAddressViewModel.addAddressState.value
    val sharedState = sharedViewModel.sharedState.value
    val errorSnackBarHostState = remember { SnackbarHostState() }
    val successSnackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        addAddressViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is SignInUIEvent.ShowMessage -> {
                    errorSnackBarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }
    LaunchedEffect(key1 = addAddressState.response) {
        when (addAddressState.response) {
            is Resource.Loading -> {
                // Display loading UI
                addAddressViewModel.onEvent(AddAddressEvent.UpdateLoading(true))
            }
            is Resource.Success -> {
                // Display success UI with data
                addAddressViewModel.onEvent(AddAddressEvent.UpdateLoading(false))
                if (addAddressState.response.data?.statusCode == JSonStatusCode.SUCCESS) {
                    if (addAddressState.forWhat == FOR_ADD) {
                        successSnackBarHostState.showSnackbar(message = UIText.StringResource(R.string.success_add_address).asString(context))
                    }else{
                        successSnackBarHostState.showSnackbar(message = UIText.StringResource(R.string.success_edit_address).asString(context))
                    }
                    delay(500)  // the delay of 0.5 seconds
                    sharedViewModel.selectLocation(0.00,0.00)
                    onSuccess()
                }
            }
            is Resource.Error -> {
                // Display error UI with message
                addAddressViewModel.onEvent(AddAddressEvent.UpdateLoading(false))
                when (addAddressState.response.data?.statusCode) {
                    JSonStatusCode.EXPIRED_TOKEN -> {
                        errorSnackBarHostState.showSnackbar(
                            message = UIText.StringResource(R.string.expired_token).asString(context)
                        )
                        delay(500)  // the delay of 0.5 seconds
                        onExpiredToken()
                    }
                    JSonStatusCode.INTERNET_CONNECTION -> {
                        errorSnackBarHostState
                            .showSnackbar(message = UIText.StringResource(R.string.not_connection_internet).asString(context))
                    }
                    JSonStatusCode.SERVER_CONNECTION -> {
                        errorSnackBarHostState
                            .showSnackbar(message = UIText.StringResource(R.string.server_connection_error).asString(context))
                    }
                }
            }
        }
    }

    Scaffold (
        topBar = {
            AppTopAppBar(
                title = stringResource(id = addAddressState.actionTitleId),
                isBackVisible = true,
                onBack = {
                    sharedViewModel.selectLocation(0.00,0.00)
                    onNavUp()
                }
            )
        },
        snackbarHost = {
            SnackbarHost(errorSnackBarHostState) {
                AppSnackBar(it)
            }
            SnackbarHost(successSnackBarHostState) {
                AppSnackBar(it,false)
            }
        },
        content = {paddingValue ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValue)
            ) {
                AddAddressTopScreen(
                    title = addAddressState.title,
                    address = addAddressState.address,
                    onTitleUpdate = {newValue ->
                        addAddressViewModel.onEvent(AddAddressEvent.UpdateTitleTextFieldState(newValue))
                    },
                    onAddressUpdate = {newValue ->
                        addAddressViewModel.onEvent(AddAddressEvent.UpdateAddressTextFieldState(newValue))
                    }
                )
                AddAddressBottomScreen(onSelectLocation = {
                    if (addAddressState.id != 0){
                        onSelectLocation(FOR_EDIT_LOCATION)
                    }else{
                        onSelectLocation(FOR_ADD_LOCATION)
                    }
                })
                SecondaryButton(
                    stringId = addAddressState.actionTitleId,
                    onClick = {
                        if (addAddressState.forWhat == Constants.FOR_ADD){
                            addAddressViewModel.onEvent(AddAddressEvent.UpdateSelectedLocation(sharedState.latitude!!,sharedState.longitude!!))
                        }else{
                            if (sharedState.latitude != 0.00 && sharedState.longitude != 0.00){
                                addAddressViewModel.onEvent(AddAddressEvent.UpdateSelectedLocation(sharedState.latitude!!,sharedState.longitude!!))
                            }
                        }
                    addAddressViewModel.onEvent(AddAddressEvent.AddAddressClicked)
                })
            }
        }
    )
    BackHandler {
        // your action
        sharedViewModel.selectLocation(0.00,0.00)
        onNavUp()
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddressTopScreen(
    title: String,
    address: String,
    onTitleUpdate: (String) -> Unit,
    onAddressUpdate: (String) -> Unit
) {
    CardColumnMediumCorner(
        cardModifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl)
            {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = title,
                    onValueChange = onTitleUpdate,
                    textStyle = MaterialTheme.typography.titleMedium,
                    placeholder = {
                        TextBodyMedium(text = stringResource(id = R.string.title))
                    },
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                    maxLines = 1
                )
            }
            Image(
                painter = painterResource(id = R.mipmap.ic_grouping_blue),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl)
            {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = address,
                    onValueChange = onAddressUpdate,
                    textStyle = MaterialTheme.typography.titleMedium,
                    placeholder = {
                        TextBodyMedium(text = stringResource(id = R.string.enter_your_address))
                    },
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                    maxLines = 3
                )
            }
            Image(
                painter = painterResource(id = R.mipmap.ic_circle_address_blue),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AddAddressBottomScreen(
    onSelectLocation: () -> Unit
) {
    CardRowMediumCorner(
        cardModifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        rowModifier = Modifier.clickable { onSelectLocation() }
    ) {
        TextTitleSmall(
            text = stringResource(id = R.string.select_google_map),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            textAlign = TextAlign.End
        )
        Image(
            painter = painterResource(id = R.mipmap.ic_google_map),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }
}
