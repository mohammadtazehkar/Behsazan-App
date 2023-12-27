package com.example.behsaz.ui.screens

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.behsaz.R
import com.example.behsaz.presentation.events.AddServiceEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.viewmodels.AddServiceViewModel
import com.example.behsaz.presentation.viewmodels.AddServiceViewModelFactory
import com.example.behsaz.presentation.viewmodels.SharedViewModel
import com.example.behsaz.ui.components.AppErrorSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.CardMediumCorner
import com.example.behsaz.ui.components.CardRowMediumCorner
import com.example.behsaz.ui.components.SecondaryButton
import com.example.behsaz.ui.components.TextBodyMedium
import com.example.behsaz.ui.components.TextTitleSmall
import com.example.behsaz.ui.components.TextTitleSmallPrimary
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.UIText
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServiceScreen(
    sharedViewModel: SharedViewModel,
    categoryId: Int,
    categoryTitle: String,
    onSelectLocation: () -> Unit,
    onShowLocation: () -> Unit,
    onNavUp: () -> Unit
) {
    val addServiceViewModel: AddServiceViewModel = viewModel(
//        factory = AddServiceViewModelFactory(
//            categoryId,
//            categoryTitle.trim().ifEmpty { stringResource(id = R.string.select_category) }
//        )
    )
    val context = LocalContext.current
    val addServiceState = addServiceViewModel.addServiceState.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        addServiceViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is SignInUIEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }

    if (addServiceState.myAddressListDialogVisible){
        MyAddressListDialog(
            onDismissRequest = {
                addServiceViewModel.onEvent(AddServiceEvent.UpdateMyAddressListDialog)
            },
            onConfirmation = {selectedAddress ->
                sharedViewModel.selectLocation(selectedAddress.mapPoint.split(',')[0].toDouble(),selectedAddress.mapPoint.split(',')[1].toDouble())
                addServiceViewModel.onEvent(AddServiceEvent.SelectMyAddress(selectedAddress.id,
                    selectedAddress.address,
                    selectedAddress.mapPoint.split(',')[0].toDouble(),
                    selectedAddress.mapPoint.split(',')[1].toDouble()))
            },
            myAddresses = addServiceState.myAddressListState
        )
    }
    if (addServiceState.categoryListDialogVisible){
        CategoryListDialog(
            onDismissRequest = {
                addServiceViewModel.onEvent(AddServiceEvent.UpdateCategoryListDialog)
            },
            onConfirmation = {selectedCategory ->
                addServiceViewModel.onEvent(AddServiceEvent.SelectCategory(selectedCategory.id,selectedCategory.title))
            },
            categories = addServiceState.categoryListState
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
            SnackbarHost(snackbarHostState) {
                AppErrorSnackBar(it.visuals.message)
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
//                openCategoryListDialog.value = true
                        addServiceViewModel.onEvent(AddServiceEvent.UpdateCategoryListDialog)
                    }
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl)
                    {
                        TextTitleSmall(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            text = addServiceState.categoryTitle,
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
                        addServiceViewModel.onEvent(AddServiceEvent.UpdateMyAddressListDialog)
                    },
                    onSelectLocation = {
                        if (addServiceState.myAddressId == 0){
                            onSelectLocation()
                        }else{
                            Log.i("mamali","AddServiceScreen lat : ${addServiceState.latitude}")
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

    when (addServiceState.responseAddService) {
        is Resource.Loading -> {
            // Display loading UI
        }
        is Resource.Success -> {
            // Display success UI with data
        }
        is Resource.Error -> {
            // Display error UI with message
        }
    }

    when (addServiceState.responseMyAddressList) {
        is Resource.Loading -> {
            // Display loading UI
        }
        is Resource.Success -> {
            // Display success UI with data
            addServiceViewModel.onEvent(AddServiceEvent.PrepareMyAddressList)
        }
        is Resource.Error -> {
            // Display error UI with message
        }
    }

    when (addServiceState.responseCategoryList) {
        is Resource.Loading -> {
            // Display loading UI
        }
        is Resource.Success -> {
            // Display success UI with data
            addServiceViewModel.onEvent(AddServiceEvent.PrepareCategoryList)
        }
        is Resource.Error -> {
            // Display error UI with message
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
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
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
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