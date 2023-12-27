package com.example.behsaz.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.behsaz.ui.models.TextInputData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputItem(
    state: String,
    item: TextInputData,
    modifier: Modifier = Modifier,
    isEditable: Boolean = true,
    onValueChange: (Int, String) -> Unit,
    onDone: () -> Unit = {}
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(modifier = modifier.padding(bottom = 8.dp)) {
            OutlinedTextField(
                enabled = isEditable,
                value = state,
                onValueChange = {
                    onValueChange(item.type, it)
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 56.dp),
                shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    TextBodyMedium(text = item.label)
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = item.keyboardType,
                    imeAction = item.imedAction
                ),
                keyboardActions = KeyboardActions(onDone = {onDone()})

            )

            Column(
                modifier = modifier
                    .clip(shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.CenterStart),
            ) {
                Image(
                    modifier = modifier
                        .size(56.dp)
                        .padding(8.dp),
                    painter = painterResource(id = item.imageResourceId), contentDescription = null
                )
            }
        }

    }
}
