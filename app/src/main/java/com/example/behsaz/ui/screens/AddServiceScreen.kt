package com.example.behsaz.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
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
import com.example.behsaz.presentation.events.AddServiceEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.viewmodels.AddServiceViewModel
import com.example.behsaz.presentation.viewmodels.SharedViewModel
import com.example.behsaz.ui.components.AppSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.CardMediumCorner
import com.example.behsaz.ui.components.CardRowMediumCorner
import com.example.behsaz.ui.components.SecondaryButton
import com.example.behsaz.ui.components.TextBodyMedium
import com.example.behsaz.ui.components.TextLabelSmall
import com.example.behsaz.ui.components.TextTitleSmallPrimary
import com.example.behsaz.utils.Constants
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.JSonStatusCode.EXPIRED_TOKEN
import com.example.behsaz.utils.JSonStatusCode.INTERNET_CONNECTION
import com.example.behsaz.utils.JSonStatusCode.SERVER_CONNECTION
import com.example.behsaz.utils.JSonStatusCode.SUCCESS
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.UIText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddServiceScreen(
    sharedViewModel: SharedViewModel,
    addServiceViewModel: AddServiceViewModel = hiltViewModel(),
    onSelectLocation: () -> Unit,
    onShowLocation: () -> Unit,
    onExpiredToken: () -> Unit,
    onNavUp: () -> Unit
) {
    val context = LocalContext.current
    val addServiceState = addServiceViewModel.addServiceState.value
    val errorSnackBarHostState = remember { SnackbarHostState() }
    val successSnackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        addServiceViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is SignInUIEvent.ShowMessage -> {
                    errorSnackBarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }
    LaunchedEffect(key1 = addServiceState.responseAddService) {
        when (addServiceState.responseAddService) {
            is Resource.Loading -> {
                // Display loading UI
                addServiceViewModel.onEvent(AddServiceEvent.UpdateLoading(true))
            }
            is Resource.Success -> {
                // Display success UI with data
                addServiceViewModel.onEvent(AddServiceEvent.UpdateLoading(false))
                if (addServiceState.responseAddService.data?.statusCode == SUCCESS) {
                    successSnackBarHostState.showSnackbar(message = UIText.StringResource(R.string.success_add_service).asString(context))
                    sharedViewModel.selectLocation(0.00,0.00)
                    addServiceViewModel.onEvent(AddServiceEvent.SuccessfullyAddService)
                }
            }
            is Resource.Error -> {
                // Display error UI with message
                addServiceViewModel.onEvent(AddServiceEvent.UpdateLoading(false))
                when (addServiceState.responseMyAddressList.data?.statusCode) {
                    EXPIRED_TOKEN -> {
                        errorSnackBarHostState.showSnackbar(
                            message = UIText.StringResource(R.string.expired_token).asString(context)
                        )
                        delay(500)  // the delay of 0.5 seconds
                        onExpiredToken()
                    }
                    INTERNET_CONNECTION -> {
                        errorSnackBarHostState
                            .showSnackbar(message = UIText.StringResource(R.string.not_connection_internet).asString(context))
                    }
                    SERVER_CONNECTION -> {
                        errorSnackBarHostState
                            .showSnackbar(message = UIText.StringResource(R.string.server_connection_error).asString(context))
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = addServiceState.responseMyAddressList) {
        when (addServiceState.responseMyAddressList) {
            is Resource.Loading -> {
                // Display loading UI
                addServiceViewModel.onEvent(AddServiceEvent.UpdateLoading(true))
            }
            is Resource.Success -> {
                // Display success UI with data
                addServiceViewModel.onEvent(AddServiceEvent.UpdateLoading(false))
                if (addServiceState.responseMyAddressList.data?.statusCode == SUCCESS) {
                    addServiceViewModel.onEvent(AddServiceEvent.PrepareMyAddressList)
                    if (addServiceState.responseMyAddressList.data?.data!!.isNotEmpty()) {
                        addServiceViewModel.onEvent(AddServiceEvent.UpdateMyAddressListDialog(true))
                    }else{
                        errorSnackBarHostState.showSnackbar(message = UIText.StringResource(R.string.empty_address_dialog).asString(context))
                    }
                }
            }
            is Resource.Error -> {
                // Display error UI with message
                addServiceViewModel.onEvent(AddServiceEvent.UpdateLoading(false))
                when (addServiceState.responseMyAddressList.data?.statusCode) {
                    EXPIRED_TOKEN -> {
                        errorSnackBarHostState.showSnackbar(
                            message = UIText.StringResource(R.string.expired_token).asString(context)
                        )
                        delay(500)  // the delay of 0.5 seconds
                        onExpiredToken()
                    }
                    INTERNET_CONNECTION -> {
                        errorSnackBarHostState
                            .showSnackbar(message = UIText.StringResource(R.string.not_connection_internet).asString(context))
                    }
                    SERVER_CONNECTION -> {
                        errorSnackBarHostState
                            .showSnackbar(message = UIText.StringResource(R.string.server_connection_error).asString(context))
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = addServiceState.responseSubCategoryList) {
        when (addServiceState.responseSubCategoryList) {
            is Resource.Loading -> {
                // Display loading UI
                addServiceViewModel.onEvent(AddServiceEvent.UpdateLoading(true))
            }
            is Resource.Success -> {
                // Display success UI with data
                addServiceViewModel.onEvent(AddServiceEvent.UpdateLoading(false))
                if (addServiceState.responseSubCategoryList.data?.statusCode == SUCCESS) {
                    addServiceViewModel.onEvent(AddServiceEvent.PrepareSubCategoryList)
                    addServiceViewModel.onEvent(AddServiceEvent.UpdateSubCategoryListDialog(true))
                }
            }
            is Resource.Error -> {
                // Display error UI with message
                addServiceViewModel.onEvent(AddServiceEvent.UpdateLoading(false))
                when (addServiceState.responseSubCategoryList.data?.statusCode) {
                    INTERNET_CONNECTION -> {
                        errorSnackBarHostState.showSnackbar(message = UIText.StringResource(R.string.not_connection_internet).asString(context))
                    }
                    SERVER_CONNECTION -> {
                        errorSnackBarHostState.showSnackbar(message = UIText.StringResource(R.string.server_connection_error).asString(context))
                    }
                }
            }
        }
    }

    if (addServiceState.myAddressListDialogVisible){
        MyAddressListDialog(
            onDismissRequest = {
                addServiceViewModel.onEvent(AddServiceEvent.UpdateMyAddressListDialog(false))
            },
            onConfirmation = {selectedAddress ->
                sharedViewModel.selectLocation(selectedAddress.mapPoint.split(',')[0].toDouble(),selectedAddress.mapPoint.split(',')[1].toDouble())
                addServiceViewModel.onEvent(AddServiceEvent.SelectMyAddress(selectedAddress.id,
                    selectedAddress.address,
                    selectedAddress.mapPoint.split(',')[0].toDouble(),
                    selectedAddress.mapPoint.split(',')[1].toDouble()))
                addServiceViewModel.onEvent(AddServiceEvent.UpdateMyAddressListDialog(false))
            },
            myAddresses = addServiceState.myAddressListState
        )
    }
    if (addServiceState.subCategoryListDialogVisible){
        SubCategoryListDialog(
            onDismissRequest = {
                addServiceViewModel.onEvent(AddServiceEvent.UpdateSubCategoryListDialog(false))
            },
            onConfirmation = {selectedSubCategory ->
                addServiceViewModel.onEvent(AddServiceEvent.SelectSubCategory(selectedSubCategory.id,selectedSubCategory.title))
                addServiceViewModel.onEvent(AddServiceEvent.UpdateSubCategoryListDialog(false))
            },
            subCategories = addServiceState.subCategoryListState
        )
    }

    Scaffold (
        topBar = {
            AppTopAppBar(
                title = stringResource(id = R.string.add_service),
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

                CardRowMediumCorner(
                    cardModifier = Modifier.padding(16.dp),
                    rowModifier = Modifier.clickable {
                        addServiceViewModel.onEvent(AddServiceEvent.GetSubCategoryList)
                    }
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl)
                    {
                        TextLabelSmall(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            text = addServiceState.subCategoryTitle.ifEmpty {
                                stringResource(
                                    R.string.select_category,
                                    addServiceState.categoryTitle
                                )

                            },
                            textAlign = TextAlign.Start
                        )
                    }
                    Image(
                        painter = painterResource(id = R.mipmap.ic_grouping_blue),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }

                AddServiceMiddleScreen(
                    address = addServiceState.address,
                    onAddressUpdate = {newValue ->
                        addServiceViewModel.onEvent(AddServiceEvent.UpdateAddressTextFieldState(newValue))
                    },
                    onOpenDialog = {
                        addServiceViewModel.onEvent(AddServiceEvent.GetMyAddressList)
                    },
                    onSelectLocation = {
                        if (addServiceState.myAddressId == 0){
                            onSelectLocation()
                        }else{
                            sharedViewModel.selectLocation(addServiceState.latitude,addServiceState.longitude)
                            onShowLocation()
                        }
                    }
                )
                AddServiceBottomScreen(
                    description = addServiceState.description,
                    omDescriptionUpdate = {newValue ->
                        addServiceViewModel.onEvent(AddServiceEvent.UpdateDescriptionTextFieldState(newValue))
                    }
                )
                SecondaryButton(stringId = R.string.add_service, onClick = {
                    addServiceViewModel.onEvent(AddServiceEvent.AddServiceClicked(sharedViewModel.sharedState.value.latitude!!,sharedViewModel.sharedState.value.longitude!!))
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

@Composable
fun AddServiceMiddleScreen(
    address: String,
    onAddressUpdate: (String) -> Unit,
    onOpenDialog: () -> Unit,
    onSelectLocation: () -> Unit,
) {
    CardMediumCorner(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimary)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onSelectLocation) {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_google_map),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }
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
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                        ),
                        maxLines = 3
                    )
                }
                Image(
                    painter = painterResource(id = R.mipmap.ic_home_blue),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }

            Row(
                modifier = Modifier
                    .clickable {
                        onOpenDialog()
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextTitleSmallPrimary(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    text = stringResource(id = R.string.select_from_my_adresses),
                    textAlign = TextAlign.End
                )
                Image(
                    painter = painterResource(id = R.mipmap.ic_circle_address_blue),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServiceBottomScreen(
    description: String,
    omDescriptionUpdate: (String) -> Unit
) {
    CardRowMediumCorner(
        cardModifier = Modifier.padding(16.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl)
        {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .height(136.dp),
                value = description,
                onValueChange = omDescriptionUpdate,
                textStyle = MaterialTheme.typography.titleMedium,
                placeholder = {
                    TextBodyMedium(
                        text = stringResource(id = R.string.add_service_description),
                        textAlign = TextAlign.Justify
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
        }
        Image(
            painter = painterResource(id = R.mipmap.ic_description_blue),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }
}