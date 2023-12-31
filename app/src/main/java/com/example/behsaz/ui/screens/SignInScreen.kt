package com.example.behsaz.ui.screens

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.behsaz.R
import com.example.behsaz.presentation.constants.AppKeyboard
import com.example.behsaz.presentation.constants.SignInInputTypes.PASSWORD
import com.example.behsaz.presentation.constants.SignInInputTypes.USERNAME
import com.example.behsaz.ui.components.AppErrorSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.TextInputItem
import com.example.behsaz.ui.components.CardBoxMediumCorner
import com.example.behsaz.ui.components.PrimaryButtonExtraSmallCorner
import com.example.behsaz.ui.components.PrimaryOutlinedButtonExtraSmallCorner
import com.example.behsaz.presentation.events.SignInEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.viewmodels.SignInViewModel
import com.example.behsaz.ui.components.ProgressBarDialog
import com.example.behsaz.ui.models.TextInputData
import com.example.behsaz.utils.Resource
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val context = LocalContext.current
    val signInState = signInViewModel.signInState.value
    val snackbarHostState = remember { SnackbarHostState() }

    KeyboardHandler(signInViewModel)
    LaunchedEffect(key1 = true) {
        signInViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is SignInUIEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }
    if (signInState.isLoading) {
        ProgressBarDialog(
            onDismissRequest = {
                signInViewModel.onEvent(SignInEvent.UpdateLoading(false))
            }
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                AppErrorSnackBar(it.visuals.message)
            }
        },
        topBar = {
            AppTopAppBar(
                title = stringResource(id = R.string.login)
            )
        },
        content = { paddingValues ->
            val modifier = Modifier.padding(paddingValues)
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                CardBoxMediumCorner(
                    cardModifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                    )
                    {
                        AnimatedVisibility(
                            visible = signInState.keyboardState == AppKeyboard.Closed,
                            enter = slideInVertically() + fadeIn(initialAlpha = 0.3f),
                            exit = slideOutVertically() + shrinkVertically() + fadeOut()
                        ) {
                            Column() {
                                Branding(modifier = Modifier.fillMaxWidth(), false)
                                Spacer(modifier = Modifier.height(48.dp))
                            }
                        }

                        SignInMiddleScreen(
                            Modifier,
                            inputItems = listOf(
                                TextInputData(
                                    label =  stringResource(id = R.string.username),
                                    imageResourceId = R.mipmap.ic_username_filled_white,
                                    type = USERNAME,
                                    keyboardType = KeyboardType.Text,
                                    imedAction = ImeAction.Next
                                ),
                                TextInputData(
                                    label = stringResource(id = R.string.password),
                                    imageResourceId = R.mipmap.ic_lock_filled_white,
                                    type = PASSWORD,
                                    keyboardType = KeyboardType.Text,
                                    imedAction = ImeAction.Done
                                    ),
                            ),
                            stateList = signInState.textFieldStates,
                            onValueChange = {type,newValue ->
                                signInViewModel.onEvent(SignInEvent.UpdateTextFieldState(type,newValue))
                            },
                            onDone = {
                                signInViewModel.onEvent(
                                    SignInEvent.LoginClicked(onNavigateToHome)
                                )
                            }
                        )
                    }
                    SignInBottomScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        onNavigateToSignUp = onNavigateToSignUp,
                        onLoginClicked = {
                            signInViewModel.onEvent(
                                SignInEvent.LoginClicked(onNavigateToHome)
                            )
                        }
                    )
                }
            }
        }
    )

    when (signInState.response) {
        is Resource.Loading -> {
            // Display loading UI
            signInViewModel.onEvent(SignInEvent.UpdateLoading(true))

        }
        is Resource.Success -> {
            // Display success UI with data
            signInViewModel.onEvent(SignInEvent.UpdateLoading(false))
        }
        is Resource.Error -> {
            // Display error UI with message
            signInViewModel.onEvent(SignInEvent.UpdateLoading(false))

        }
    }
}

@Composable
fun SignInMiddleScreen(
    modifier: Modifier = Modifier,
    stateList: List<String>,
    inputItems: List<TextInputData>,
    onValueChange: (Int,String) -> Unit,
    onDone: () -> Unit
) {
    Column(modifier = modifier) {

        inputItems.forEachIndexed { index, item ->
            TextInputItem(
                state = stateList[index],
                item = item,
                onValueChange = {type,value ->
                    onValueChange(type,value)
                },
                onDone = onDone
            )
        }
    }
}

@Composable
fun SignInBottomScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignUp: () -> Unit,
    onLoginClicked: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        PrimaryOutlinedButtonExtraSmallCorner(
            modifier = Modifier.weight(1f),
            stringId = R.string.register,
            onClick = onNavigateToSignUp
        )
        Spacer(modifier = Modifier.width(8.dp))
        PrimaryButtonExtraSmallCorner(
            modifier = Modifier.weight(1f),
            stringId = R.string.login,
            onClick = onLoginClicked
        )
    }
}

@Composable
fun KeyboardHandler(signInViewModel: SignInViewModel) {
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight > screenHeight * 0.15) {
                signInViewModel.onEvent(SignInEvent.KeyboardOpen)
            } else {
                signInViewModel.onEvent(SignInEvent.KeyboardClose)
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }
}