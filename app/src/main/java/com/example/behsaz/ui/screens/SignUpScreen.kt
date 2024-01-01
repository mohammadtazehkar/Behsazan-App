package com.example.behsaz.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.behsaz.R
import com.example.behsaz.presentation.constants.SignUpInputTypes.EMAIL
import com.example.behsaz.presentation.constants.SignUpInputTypes.FIRSTNAME
import com.example.behsaz.presentation.constants.SignUpInputTypes.LASTNAME
import com.example.behsaz.presentation.constants.SignUpInputTypes.MOBILE_NUMBER
import com.example.behsaz.presentation.constants.SignUpInputTypes.PASSWORD
import com.example.behsaz.presentation.constants.SignUpInputTypes.PHONE_NUMBER
import com.example.behsaz.presentation.constants.SignUpInputTypes.REAGENT_TOKEN
import com.example.behsaz.presentation.constants.SignUpInputTypes.USERNAME
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.events.SignUpEvent
import com.example.behsaz.presentation.viewmodels.SignUpViewModel
import com.example.behsaz.ui.components.AppErrorSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.ProgressBarDialog
import com.example.behsaz.ui.components.UserInfoContent
import com.example.behsaz.ui.models.TextInputData
import com.example.behsaz.utils.Resource
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    onSignUpSubmitted: () -> Unit,
    onNavUp: () -> Unit
) {
    val context = LocalContext.current
    val signUpState = signUpViewModel.signUpState.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        signUpViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is SignInUIEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> {}
            }
        }
    }
    if (signUpState.isLoading) {
        ProgressBarDialog(
            onDismissRequest = {
                signUpViewModel.onEvent(SignUpEvent.UpdateLoading(false))
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
                title = stringResource(id = R.string.register),
                isBackVisible = true,
                onBack = onNavUp
            )
        },
        content = {paddingValues ->
            val modifier = Modifier.padding(paddingValues)
            UserInfoContent(
                modifier = modifier,
                personalInfoItems = listOf(
                    TextInputData(
                        label = stringResource(id = R.string.first_name),
                        imageResourceId = R.mipmap.ic_username_filled_white,
                        type = FIRSTNAME,
                        keyboardType = KeyboardType.Text,
                        imedAction = ImeAction.Next
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.last_name),
                        imageResourceId = R.mipmap.ic_lock_filled_white,
                        type = LASTNAME,
                        keyboardType = KeyboardType.Text,
                        imedAction = ImeAction.Next
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.phone_number),
                        imageResourceId = R.mipmap.ic_phone_filled_white,
                        type = PHONE_NUMBER,
                        keyboardType = KeyboardType.Phone,
                        imedAction = ImeAction.Next
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.mobile_number),
                        imageResourceId = R.mipmap.ic_mobile_filled_white,
                        type = MOBILE_NUMBER,
                        keyboardType = KeyboardType.Phone,
                        imedAction = ImeAction.Next
                    )
                ),
                userInfoItems = listOf(
                    TextInputData(
                        label = stringResource(id = R.string.username),
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
                        imedAction = ImeAction.Next
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.email),
                        imageResourceId = R.mipmap.ic_email_filled_white,
                        type = EMAIL,
                        keyboardType = KeyboardType.Email,
                        imedAction = ImeAction.Next
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.reagent_token),
                        imageResourceId = R.mipmap.ic_tag_filled_white,
                        type = REAGENT_TOKEN,
                        keyboardType = KeyboardType.Text,
                        imedAction = ImeAction.Done
                    )
                ),
                personalStateList = signUpState.personalTextFieldStates,
                userStateList = signUpState.userTextFieldStates,
                isEditable = true,
                onSubmitted = {
                    signUpViewModel.onEvent(
                        SignUpEvent.SignUpClicked(onSignUpSubmitted)
                    )
                },
                onPersonalValueChange = {type,newValue ->
                    signUpViewModel.onEvent(SignUpEvent.UpdatePersonalTextFieldState(type,newValue))
                },
                onUserValueChange = {type,newValue ->
                    signUpViewModel.onEvent(SignUpEvent.UpdateUserTextFieldState(type,newValue))
                }
//            onNavigateToHome = onNavigateToHome
            )
        }
    )

    when (signUpState.response) {
        is Resource.Loading -> {
            // Display loading UI
            signUpViewModel.onEvent(SignUpEvent.UpdateLoading(true))
        }
        is Resource.Success -> {
            // Display success UI with data
            signUpViewModel.onEvent(SignUpEvent.UpdateLoading(false))
        }
        is Resource.Error -> {
            // Display error UI with message
            signUpViewModel.onEvent(SignUpEvent.UpdateLoading(false))
        }
    }

}