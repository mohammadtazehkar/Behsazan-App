package com.example.behsaz.ui.screens

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.behsaz.R
import com.example.behsaz.presentation.events.ProfileEvent
import com.example.behsaz.presentation.events.RulesEvent
import com.example.behsaz.presentation.viewmodels.RulesViewModel
import com.example.behsaz.ui.components.AppErrorSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.UIText
import kotlinx.coroutines.delay

@Composable
fun RulesScreen(
    rulesViewModel : RulesViewModel = hiltViewModel(),
    onNavUp: () -> Unit
){
    val context = LocalContext.current
    val rulesState = rulesViewModel.rulesState.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = rulesState.response) {
        when (rulesState.response) {
            is Resource.Loading -> {
                // Display loading UI
                rulesViewModel.onEvent(RulesEvent.UpdateLoading(true))
            }
            is Resource.Success -> {
                // Display success UI with data
                rulesViewModel.onEvent(RulesEvent.UpdateLoading(false))
                if (rulesState.response.data?.statusCode == JSonStatusCode.SUCCESS) {
                    rulesViewModel.onEvent(RulesEvent.PrepareRules)
                }
            }
            is Resource.Error -> {
                // Display error UI with message
                rulesViewModel.onEvent(RulesEvent.UpdateLoading(false))
                when (rulesState.response.data?.statusCode) {
                    JSonStatusCode.INTERNET_CONNECTION -> {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = UIText.StringResource(R.string.not_connection_internet).asString(context),
                                actionLabel = UIText.StringResource(R.string.trye_again).asString(context),
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                rulesViewModel.onEvent(RulesEvent.GetRulesFromServer)
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
                                rulesViewModel.onEvent(RulesEvent.GetRulesFromServer)
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
                title = stringResource(id = R.string.rules),
                isBackVisible = true,
                onBack = onNavUp
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                AppErrorSnackBar(it)
            }
        }
        ) {paddingValues ->
        Column (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (rulesState.url.isNotEmpty()){
                loadWebUrl(rulesState.url)
            }
        }
    }
}

@Composable
fun loadWebUrl(url: String) {
    val context = LocalContext.current
    AndroidView(modifier = Modifier.fillMaxSize(),factory = {
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
    
}