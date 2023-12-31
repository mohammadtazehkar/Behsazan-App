package com.example.behsaz.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.behsaz.R

@Composable
fun ProgressBarDialog(
    onDismissRequest: () -> Unit,
){
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_progress))

        CardColumnMediumCorner {

            LottieAnimation(
                modifier = Modifier.size(128.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                TextBodySmall(text = stringResource(id = R.string.please_wait), textAlign = TextAlign.Justify)
            }

        }
    }

}