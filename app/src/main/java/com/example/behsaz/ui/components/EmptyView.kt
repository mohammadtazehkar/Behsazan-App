package com.example.behsaz.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.behsaz.R

@Composable
fun EmptyView(
    text: String
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_empty))

    Column {

        LottieAnimation(
//        modifier = modifier,
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )

        TextBodyMedium(text = text)

    }

}