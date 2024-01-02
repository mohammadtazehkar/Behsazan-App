package com.example.behsaz.ui.screens

import android.util.Log
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.behsaz.R
import com.example.behsaz.data.models.message.MessageListData
import com.example.behsaz.presentation.events.MessageListEvent
import com.example.behsaz.presentation.events.ProfileEvent
import com.example.behsaz.presentation.events.SignInEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.viewmodels.MessageListViewModel
import com.example.behsaz.ui.components.AppErrorSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.CardColumnMediumCorner
import com.example.behsaz.ui.components.EmptyView
import com.example.behsaz.ui.components.ProgressBarDialog
import com.example.behsaz.ui.components.TextTitleSmall
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.UIText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MessageListScreen(
    messageListViewModel: MessageListViewModel = hiltViewModel(),
    onNavUp: () -> Unit
) {
    val context = LocalContext.current
    val messageListState = messageListViewModel.messageListState.value
    val snackbarHostState = remember { SnackbarHostState() }

    if (messageListState.isLoading) {
        ProgressBarDialog(
            onDismissRequest = {
                messageListViewModel.onEvent(MessageListEvent.UpdateLoading(false))
            }
        )
    }

    LaunchedEffect(key1 = messageListState.response) {
        when (messageListState.response) {
            is Resource.Loading -> {
                // Display loading UI
                messageListViewModel.onEvent(MessageListEvent.UpdateLoading(true))

            }
            is Resource.Success -> {
                // Display success UI with data
                messageListViewModel.onEvent(MessageListEvent.UpdateLoading(false))
                if (messageListState.response.data?.statusCode == JSonStatusCode.SUCCESS) {
                    messageListViewModel.onEvent(MessageListEvent.PrepareList)
                }
            }
            is Resource.Error -> {
                // Display error UI with message
                messageListViewModel.onEvent(MessageListEvent.UpdateLoading(false))
                when (messageListState.response.data?.statusCode) {
                    JSonStatusCode.INTERNET_CONNECTION -> {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = UIText.StringResource(R.string.not_connection_internet).asString(context),
                                actionLabel = UIText.StringResource(R.string.trye_again).asString(context),
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                messageListViewModel.onEvent(MessageListEvent.GetListFromServer)
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
                                messageListViewModel.onEvent(MessageListEvent.GetListFromServer)
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

    Scaffold (
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                AppErrorSnackBar(it)
            }
        },
        topBar = {
            AppTopAppBar(
                title = stringResource(id = R.string.messages),
                isBackVisible = true,
                onBack = onNavUp
            )
        }
    ) {paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValue)
        ) {
            if (messageListState.listState.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    content = {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        items(messageListState.listState.size) { index ->
                            MessageListItem(messageListState.listState[index])
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                )
            }
            else{
                Column (
                    modifier = Modifier.fillMaxHeight().padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    EmptyView(text = stringResource(id = R.string.empty_message_list))
                }
            }
        }
    }
}

@Composable
fun MessageListItem(
    item: MessageListData
) {
    CardColumnMediumCorner {
        Image(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(56.dp),
            painter = painterResource(id = R.mipmap.ic_circle_message_green),
            contentDescription = null
        )
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            TextTitleSmall(text = item.text, textAlign = TextAlign.Justify)
        }
        Divider(
            color = MaterialTheme.colorScheme.secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = R.mipmap.ic_calendar_blue),
                contentDescription = null
            )
            TextTitleSmall(text = item.dateTime, modifier = Modifier.padding(start = 8.dp))
        }
    }
}

