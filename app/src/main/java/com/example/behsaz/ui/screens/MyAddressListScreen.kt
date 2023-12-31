package com.example.behsaz.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.behsaz.R
import com.example.behsaz.data.models.myAddress.MyAddressListData
import com.example.behsaz.presentation.events.MyAddressListEvent
import com.example.behsaz.presentation.events.MyServiceListEvent
import com.example.behsaz.presentation.viewmodels.MyAddressListViewModel
import com.example.behsaz.presentation.viewmodels.SharedViewModel
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.CardColumnMediumCorner
import com.example.behsaz.ui.components.EmptyView
import com.example.behsaz.ui.components.ProgressBarDialog
import com.example.behsaz.ui.components.TextTitleSmall
import com.example.behsaz.utils.Resource

@Composable
fun MyAddressListScreen(
    myAddressListViewModel: MyAddressListViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onAddAddressClick: () -> Unit,
    onEditAddressClick: (Int, String, String, Double, Double) -> Unit,
    onShowLocation: () -> Unit,
    onNavUp: () -> Unit
) {
    val myAddressListState = myAddressListViewModel.myAddressListState.value

    // Nested scroll for control FAB
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Hide FAB
                if (available.y < -1) {
                    myAddressListViewModel.onEvent(MyAddressListEvent.MakeFabInVisible)
                }

                // Show FAB
                if (available.y > 1) {
                    myAddressListViewModel.onEvent(MyAddressListEvent.MakeFabVisible)
                }

                return Offset.Zero
            }
        }
    }
    if (myAddressListState.isLoading) {
        ProgressBarDialog(
            onDismissRequest = {
                myAddressListViewModel.onEvent(MyAddressListEvent.UpdateLoading(false))
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = myAddressListState.fabVisible,
                enter = slideInVertically(initialOffsetY = { it * 2 }),
                exit = slideOutVertically(targetOffsetY = { it * 2 }),
            ) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = onAddAddressClick,
                    containerColor = MaterialTheme.colorScheme.primary,
                )
                {
                    Image(painterResource(id = R.mipmap.ic_add_white), contentDescription = "icon")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            AppTopAppBar(
                title = stringResource(id = R.string.my_addresses),
                isBackVisible = true,
                onBack = {
                    sharedViewModel.selectLocation(0.00,0.00)
                    onNavUp()
                }
            )
        }
    ) {paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValue)
        ) {
            if (myAddressListState.listState.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .nestedScroll(nestedScrollConnection),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(myAddressListState.listState.size) { index ->
                        MyAddressListItem(
                            item = myAddressListState.listState[index],
                            onEditAddressClick = { id, title, address, latitude, longitude ->
                                sharedViewModel.selectLocation(0.00, 0.00)
                                onEditAddressClick(id, title, address, latitude, longitude)
                            },
                            onShowLocation = {
                                val values = myAddressListState.listState[index].mapPoint.split(',')
                                sharedViewModel.selectLocation(
                                    values[0].toDouble(),
                                    values[1].toDouble()
                                )
                                onShowLocation()
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }else{
                Column (
                    modifier = Modifier.fillMaxHeight().padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    EmptyView(text = stringResource(id = R.string.empty_address_list))
                }
            }
        }

    }

    BackHandler {
        // your action
        sharedViewModel.selectLocation(0.00,0.00)
        onNavUp()
    }


    when (myAddressListState.response) {
        is Resource.Loading -> {
            // Display loading UI
            myAddressListViewModel.onEvent(MyAddressListEvent.UpdateLoading(true))
        }
        is Resource.Success -> {
            // Display success UI with data
            myAddressListViewModel.onEvent(MyAddressListEvent.UpdateLoading(false))
            myAddressListViewModel.onEvent(MyAddressListEvent.PrepareList)

        }
        is Resource.Error -> {
            // Display error UI with message
            myAddressListViewModel.onEvent(MyAddressListEvent.UpdateLoading(false))
        }
    }
}

@Composable
fun MyAddressListItem(
    item: MyAddressListData,
    onEditAddressClick: (Int, String, String, Double, Double) -> Unit,
    onShowLocation: () -> Unit
) {
    CardColumnMediumCorner(
        cardModifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    val values = item.mapPoint.split(',')
                    onEditAddressClick(item.id, item.title, item.address, values[0].toDouble(), values[1].toDouble())
                }
            ){
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.mipmap.ic_edit_blue),
                    contentDescription = null
                )
            }
            IconButton(
                onClick = onShowLocation
            ){
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.mipmap.ic_google_map),
                    contentDescription = null
                )
            }
            TextTitleSmall(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                text = item.title,
                textAlign = TextAlign.End
            )
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.mipmap.ic_home_blue),
                contentDescription = null
            )
        }
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            TextTitleSmall(text = item.address, textAlign = TextAlign.Justify)
        }
    }
}