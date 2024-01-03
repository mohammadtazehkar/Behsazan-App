package com.example.behsaz.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.behsaz.R
import com.example.behsaz.data.models.myService.MyServiceListData
import com.example.behsaz.presentation.events.MyServiceListEvent
import com.example.behsaz.presentation.viewmodels.MyServiceListViewModel
import com.example.behsaz.ui.components.AppSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.CardColumnMediumCorner
import com.example.behsaz.ui.components.EmptyView
import com.example.behsaz.ui.components.ProgressBarDialog
import com.example.behsaz.ui.components.TextLabelSmall
import com.example.behsaz.ui.components.TextTitleMedium
import com.example.behsaz.ui.components.TextTitleSmall
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.UIText
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyServiceListScreen(
    myServiceListViewModel: MyServiceListViewModel = hiltViewModel(),
    onExpiredToken: () -> Unit,
    onNavUp: () -> Unit
) {
    val context = LocalContext.current
    val myServiceListState = myServiceListViewModel.myServiceListState.value
    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = myServiceListState.isLoading,
        onRefresh = {myServiceListViewModel.onEvent(MyServiceListEvent.GetListFromServer)}
    )

    if (myServiceListState.isLoading) {
        ProgressBarDialog(
            onDismissRequest = {
                myServiceListViewModel.onEvent(MyServiceListEvent.UpdateLoading(false))
            }
        )
    }

    LaunchedEffect(key1 = myServiceListState.response) {
        when (myServiceListState.response) {
            is Resource.Loading -> {
                // Display loading UI
                myServiceListViewModel.onEvent(MyServiceListEvent.UpdateLoading(true))
            }
            is Resource.Success -> {
                // Display success UI with data
                myServiceListViewModel.onEvent(MyServiceListEvent.UpdateLoading(false))
                if (myServiceListState.response.data?.statusCode == JSonStatusCode.SUCCESS) {
                    myServiceListViewModel.onEvent(MyServiceListEvent.PrepareList)
                }
            }
            is Resource.Error -> {
                // Display error UI with message
                myServiceListViewModel.onEvent(MyServiceListEvent.UpdateLoading(false))
                when (myServiceListState.response.data?.statusCode) {
                    JSonStatusCode.EXPIRED_TOKEN -> {
                        snackbarHostState.showSnackbar(
                            message = UIText.StringResource(R.string.expired_token).asString(context)
                        )
                        delay(500)  // the delay of 0.5 seconds
                        onExpiredToken()
                    }
                    JSonStatusCode.INTERNET_CONNECTION -> {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = UIText.StringResource(R.string.not_connection_internet).asString(context),
                                actionLabel = UIText.StringResource(R.string.trye_again).asString(context),
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                myServiceListViewModel.onEvent(MyServiceListEvent.GetListFromServer)
                            }
                            SnackbarResult.Dismissed -> {
                                /* Handle snackbar dismissed */
                                Log.i("mamali","ssss")
                            }
                        }
                    }
                    JSonStatusCode.SERVER_CONNECTION -> {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = UIText.StringResource(R.string.server_connection_error).asString(context),
                                actionLabel = UIText.StringResource(R.string.trye_again).asString(context),
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                myServiceListViewModel.onEvent(MyServiceListEvent.GetListFromServer)
                            }
                            SnackbarResult.Dismissed -> {
                                /* Handle snackbar dismissed */
                            }
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = stringResource(id = R.string.my_services),
                isBackVisible = true,
                onBack = onNavUp
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                AppSnackBar(it)
            }
        }
        ) {paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValue)
        ) {

            Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                PullRefreshIndicator(
                    refreshing = myServiceListState.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = if (myServiceListState.isLoading) Color.Red else Color.Green,
                )
                if (myServiceListState.listState.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        content = {
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            items(myServiceListState.listState.size) { index ->
                                MyServiceListItem(
                                    item = myServiceListState.listState[index],
//                                    onServiceItemClick = onServiceItemClick
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    )
                }
                else {
                    Column (
                        modifier = Modifier.fillMaxHeight().padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        EmptyView(text = stringResource(id = R.string.empty_service_list))
                    }
                }
            }
        }
    }
}

@Composable
fun MyServiceListItem(
    item: MyServiceListData,
    ) {
    CardColumnMediumCorner{
        TextTitleSmall(text = "${item.serviceGroup} - ${item.serviceType}")
        Divider(
            color = MaterialTheme.colorScheme.secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(0.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                TextLabelSmall(text = item.status, modifier = Modifier.padding(horizontal = 4.dp))
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.mipmap.ic_status_blue),
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier.weight(0.4f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                TextLabelSmall(text = item.dateTime, modifier = Modifier.padding(horizontal = 4.dp))
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.mipmap.ic_calendar_blue),
                    contentDescription = null
                )
            }
            Row(
                modifier = Modifier.weight(0.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                TextLabelSmall(text = item.code, modifier = Modifier.padding(horizontal = 4.dp))
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.mipmap.ic_barcode_blue),
                    contentDescription = null
                )
            }
        }
    }
}