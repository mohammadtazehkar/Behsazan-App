package com.example.behsaz.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.behsaz.R
import com.example.behsaz.data.models.myService.MyServiceListData
import com.example.behsaz.presentation.events.MyServiceListEvent
import com.example.behsaz.presentation.viewmodels.MyServiceListViewModel
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.CardColumnMediumCorner
import com.example.behsaz.ui.components.EmptyView
import com.example.behsaz.ui.components.TextTitleMedium
import com.example.behsaz.ui.components.TextTitleSmall
import com.example.behsaz.utils.Resource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyServiceListScreen(
    myServiceListViewModel: MyServiceListViewModel = viewModel(),
    onServiceItemClick: () -> Unit,
    onNavUp: () -> Unit
) {

    val myServiceListState = myServiceListViewModel.myServiceListState.value
    val pullRefreshState = rememberPullRefreshState(
        refreshing = myServiceListState.isLoading,
        onRefresh = {myServiceListViewModel.onEvent(MyServiceListEvent.GetListFromServer)}
    )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            AppTopAppBar(
                title = stringResource(id = R.string.my_services),
                isBackVisible = true,
                onBack = onNavUp
            )
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
                                    onServiceItemClick = onServiceItemClick
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    )
                } else {
                    EmptyView(text = stringResource(id = R.string.empty_service_list))
                }
            }
        }



    when (myServiceListState.response) {
        is Resource.Loading -> {
            // Display loading UI
            myServiceListViewModel.onEvent(MyServiceListEvent.UpdateLoading(true))
        }
        is Resource.Success -> {
            // Display success UI with data
            myServiceListViewModel.onEvent(MyServiceListEvent.UpdateLoading(false))
            myServiceListViewModel.onEvent(MyServiceListEvent.PrepareList)
        }
        is Resource.Error -> {
            // Display error UI with message
            myServiceListViewModel.onEvent(MyServiceListEvent.UpdateLoading(false))
        }
    }
}

@Composable
fun MyServiceListItem(
    item: MyServiceListData,
    onServiceItemClick: () -> Unit,
    ) {
    CardColumnMediumCorner(
        columnModifier = Modifier.clickable {
            onServiceItemClick()
        }
    ) {
        TextTitleMedium(text = item.serviceGroup)
        Divider(
            color = MaterialTheme.colorScheme.secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var iconsList = listOf(
                R.mipmap.ic_status_blue,
                R.mipmap.ic_calendar_blue,
                R.mipmap.ic_barcode_blue
            )
            var titleList = listOf(item.status, item.dateTime, item.code)
            repeat(iconsList.size) {
                MyServiceListItemBottom(
                    modifier = Modifier
                        .weight(0.33f),
                    iconsList[it],
                    titleList[it]
                )
            }
        }
    }
}

@Composable
fun MyServiceListItemBottom(
    modifier: Modifier = Modifier,
    iconId: Int,
    title: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        TextTitleSmall(text = title, modifier = Modifier.padding(horizontal = 4.dp))
        Image(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = null
        )
    }
}
