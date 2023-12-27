package com.example.behsaz.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.behsaz.R
import com.example.behsaz.presentation.constants.SignUpInputTypes.REAGENT_TOKEN
import com.example.behsaz.ui.models.TextInputData

@Composable
fun UserInfoContent(
    modifier: Modifier = Modifier,
    personalInfoItems: List<TextInputData>,
    userInfoItems: List<TextInputData>,
    personalStateList: List<String>,
    userStateList: List<String>,
    isEditable: Boolean,
    isProfile: Boolean = false,
    onSubmitted: () -> Unit,
    onPersonalValueChange: (Int,String) -> Unit,
    onUserValueChange: (Int,String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        UserPersonalInfo(
            personalInfoItems,
            stringResource(id = R.string.personal_info),
            personalStateList,
            isEditable,
            onValueChange = onPersonalValueChange,
        )
        UserPersonalInfo(
            userInfoItems,
            stringResource(id = R.string.user_info),
            userStateList,
            isEditable,
            isProfile,
            onValueChange = onUserValueChange,
            onDone = onSubmitted
        )
        if (isEditable) {
            val stringId = if (isProfile) R.string.submit else R.string.register
            SecondaryButton(stringId = stringId, onClick = onSubmitted)
        }
    }
}


@Composable
fun UserPersonalInfo(
    infoItems: List<TextInputData>,
    title: String,
    stateList: List<String>,
    isEditable: Boolean = true,
    isProfileLastItem: Boolean = false,
    onValueChange: (Int,String) -> Unit,
    onDone : () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        CardColumnMediumCorner (
            cardModifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            infoItems.forEachIndexed { index, item ->
                if (isProfileLastItem && index == REAGENT_TOKEN) {
                    TextInputItem(
                        state = stateList[index],
                        item = item,
                        isEditable = false,
                        onValueChange = { type, value ->
                            onValueChange(type, value)
                        },
                        onDone = onDone
                    )
                }else{
                    TextInputItem(
                        state = stateList[index],
                        item = item,
                        isEditable = isEditable,
                        onValueChange = { type, value ->
                            onValueChange(type, value)
                        },
                        onDone = onDone
                    )
                }
            }
        }
        Card(
            shape = MaterialTheme.shapes.extraSmall,
            modifier = Modifier.align(Alignment.TopCenter),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onPrimary)
            ) {
                TextTitleSmallPrimary(modifier = Modifier.padding(8.dp),text = title)
            }
        }
    }
}