package com.example.behsaz.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.behsaz.R
import com.example.behsaz.presentation.viewmodels.SplashViewModel
import com.example.behsaz.ui.components.CardBoxMediumCorner
import com.example.behsaz.ui.components.TextHeadlineSmallPrimary
import com.example.behsaz.ui.components.TextTitleMedium
import com.example.behsaz.ui.components.TextTitleMediumPrimary
import kotlinx.coroutines.delay
import javax.inject.Inject

private const val IndicatorSize = 12
private const val AnimationDurationMillis = 300
private const val AnimationDelayMillis = AnimationDurationMillis / 3
private const val SplashWaitTime: Long = 2000

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = viewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {

//    val currentOnTimeout by rememberUpdatedState(onTimeout)
//
    LaunchedEffect(Unit) {
        delay(SplashWaitTime)
        if (splashViewModel.hasUserData.value){
            onNavigateToHome()
        }else{
            onNavigateToSignIn()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CardBoxMediumCorner(
            cardModifier = Modifier.padding(16.dp),
            boxModifier = Modifier.fillMaxSize()
        ) {
            Branding(modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),true)
            Waiting(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
fun Branding(
    modifier: Modifier = Modifier,
    isSplash: Boolean
) {
    val configuration = LocalConfiguration.current

    val logoSize = if (isSplash) {
        0.5 * configuration.screenWidthDp.dp
    }else{
        0.3 * configuration.screenWidthDp.dp
    }
    Column(modifier = modifier) {
        Image(
            modifier = modifier
                .size(logoSize)
                .padding(bottom = 24.dp),
            painter = painterResource(R.drawable.behsaz_logo),
            contentDescription = null,
            alignment = Alignment.Center

        )
        if (isSplash){
            TextTitleMedium(
                modifier = modifier,
                text = stringResource(id = R.string.app_description)
            )
            TextHeadlineSmallPrimary(
                modifier = modifier.padding(top = 32.dp),
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.Bold
            )
        }else{
            TextTitleMediumPrimary(modifier = modifier,text = stringResource(id = R.string.app_name), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Waiting(modifier: Modifier = Modifier) {

    Row(modifier = modifier.padding(bottom = 16.dp)) {
        LoadingIndicator()
//        Text(
//            text = stringResource(id = R.string.loading),
//            color = MaterialTheme.colorScheme.primary,
//            fontWeight = FontWeight.Bold,
//            style = MaterialTheme.typography.titleSmall
//        )
    }

}

//region Loading
@Composable
private fun LoadingDot(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = color)
    )
}

@Composable
private fun LoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    indicatorSpacing: Dp = 4.dp,
) {
    // 1
    val animatedValues = List(3) { index ->
        var animatedValue by remember(key1 = Unit) { mutableStateOf(0f) }
        LaunchedEffect(key1 = Unit) {
            animate(
                initialValue = IndicatorSize / 2f,
                targetValue = -IndicatorSize / 2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = AnimationDurationMillis),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(AnimationDelayMillis * index),
                ),
            ) { value, _ -> animatedValue = value }
        }
        animatedValue
    }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        // 2
        animatedValues.forEach { animatedValue ->
            LoadingDot(
                modifier = Modifier
                    .padding(horizontal = indicatorSpacing)
                    .width(IndicatorSize.dp)
                    .aspectRatio(1f)
                    // 3
                    .offset(y = animatedValue.dp),
                color = color,
            )
        }
    }
}
//endregion