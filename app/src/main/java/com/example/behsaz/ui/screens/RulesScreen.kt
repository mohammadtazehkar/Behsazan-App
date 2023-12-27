package com.example.behsaz.ui.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.behsaz.R
import com.example.behsaz.presentation.viewmodels.RulesViewModel
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.utils.Resource

@Composable
fun RulesScreen(
    rulesViewModel : RulesViewModel = viewModel(),
    onNavUp: () -> Unit
){
    val rulesState = rulesViewModel.rulesState.value

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppTopAppBar(
            title = stringResource(id = R.string.rules),
            isBackVisible = true,
            onBack = onNavUp
        )
    }

    when (rulesState.response) {
        is Resource.Loading -> {
            // Display loading UI
        }
        is Resource.Success -> {
            // Display success UI with data
            rulesViewModel.setUrl()
            loadWebUrl(url = rulesState.response.data?.data!!)

        }
        is Resource.Error -> {
            // Display error UI with message
        }
    }
}

@Composable
fun loadWebUrl(url: String) {
    val context = LocalContext.current
    AndroidView(factory = {
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
    
}