package com.example.behsaz.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.behsaz.R
import com.example.behsaz.presentation.constants.SignUpInputTypes
import com.example.behsaz.presentation.events.ProfileEvent
import com.example.behsaz.presentation.events.SignInEvent
import com.example.behsaz.presentation.events.SignInUIEvent
import com.example.behsaz.presentation.events.SignUpEvent
import com.example.behsaz.presentation.viewmodels.ProfileViewModel
import com.example.behsaz.ui.components.AppErrorSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.UserInfoContent
import com.example.behsaz.ui.models.TextInputData
import com.example.behsaz.utils.Resource
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel(),
    onEditSubmitted: () -> Unit,
    onNavUp: () -> Unit
) {

    val context = LocalContext.current
    val profileState = profileViewModel.profileState.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        profileViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is SignInUIEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = stringResource(id = R.string.profile),
                isBackVisible = true,
                onBack = {
                    if (profileState.isEditable){
                        profileViewModel.onEvent(ProfileEvent.ChangeEditState)
                    }else{
                        onNavUp()
                    }
                },
                isEditVisible = !profileState.isEditable,
                onEditClick = {
                    profileViewModel.onEvent(ProfileEvent.ChangeEditState)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                AppErrorSnackBar(it.visuals.message)
            }
        },
        content = { paddingValues ->
            val modifier = Modifier.padding(paddingValues)
            UserInfoContent(
                modifier = modifier,
                personalInfoItems = listOf(
                    TextInputData(
                        label = stringResource(id = R.string.first_name),
                        imageResourceId = R.mipmap.ic_username_filled_white,
                        type = SignUpInputTypes.FIRSTNAME,
                        keyboardType = KeyboardType.Text,
                        imedAction = ImeAction.Next
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.last_name),
                        imageResourceId = R.mipmap.ic_lock_filled_white,
                        type = SignUpInputTypes.LASTNAME,
                        keyboardType = KeyboardType.Text,
                        imedAction = ImeAction.Next
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.phone_number),
                        imageResourceId = R.mipmap.ic_phone_filled_white,
                        type = SignUpInputTypes.PHONE_NUMBER,
                        keyboardType = KeyboardType.Phone,
                        imedAction = ImeAction.Next
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.mobile_number),
                        imageResourceId = R.mipmap.ic_mobile_filled_white,
                        type = SignUpInputTypes.MOBILE_NUMBER,
                        keyboardType = KeyboardType.Phone,
                        imedAction = ImeAction.Next
                    )
                ),
                userInfoItems = listOf(
                    TextInputData(
                        label = stringResource(id = R.string.username),
                        imageResourceId = R.mipmap.ic_username_filled_white,
                        type = SignUpInputTypes.USERNAME,
                        keyboardType = KeyboardType.Text,
                        imedAction = ImeAction.Next
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.password),
                        imageResourceId = R.mipmap.ic_lock_filled_white,
                        type = SignUpInputTypes.PASSWORD,
                        keyboardType = KeyboardType.Text,
                        imedAction = ImeAction.Next
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.email),
                        imageResourceId = R.mipmap.ic_email_filled_white,
                        type = SignUpInputTypes.EMAIL,
                        keyboardType = KeyboardType.Email,
                        imedAction = ImeAction.Done
                    ),
                    TextInputData(
                        label = stringResource(id = R.string.reagent_token),
                        imageResourceId = R.mipmap.ic_tag_filled_white,
                        type = SignUpInputTypes.REAGENT_TOKEN,
                        keyboardType = KeyboardType.Text,
                        imedAction = ImeAction.Done
                    )
                ),
                personalStateList = profileState.personalTextFieldStates,
                userStateList = profileState.userTextFieldStates,
                isEditable = profileState.isEditable,
                isProfile = true,
                onPersonalValueChange = {type,newValue ->
                    profileViewModel.onEvent(ProfileEvent.UpdatePersonalTextFieldState(type,newValue))
                },
                onUserValueChange = {type,newValue ->
                    profileViewModel.onEvent(ProfileEvent.UpdateUserTextFieldState(type,newValue))
                },
                onSubmitted = {
                    profileViewModel.onEvent(
                        ProfileEvent.UpdateClicked(onEditSubmitted)
                    )
                },
//                onNavigateToHome = onNavigateToHome
            )
        }
    )

    when (profileState.getProfileResponse) {
        is Resource.Loading -> {
            // Display loading UI
        }
        is Resource.Success -> {
            // Display success UI with data
            profileViewModel.onEvent(ProfileEvent.SetRemoteProfileData)
        }
        is Resource.Error -> {
            // Display error UI with message
        }
    }

    when (profileState.updateProfileResponse) {
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

}