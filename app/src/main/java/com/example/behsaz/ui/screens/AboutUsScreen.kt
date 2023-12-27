package com.example.behsaz.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.behsaz.R
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.CardMediumCorner
import com.example.behsaz.ui.components.TextLabelSmall
import com.example.behsaz.ui.components.TextTitleMedium
import com.example.behsaz.ui.components.TextTitleMediumPrimary
import com.example.behsaz.ui.components.TextTitleSmall

@Composable
fun AboutUsScreen(
    onNavUp: () -> Unit
) {
    val image = ImageBitmap.imageResource(R.drawable.logo_pattern)
    val brush = remember(image) {
        ShaderBrush(
            ImageShader(
                image,
                TileMode.Repeated,
                TileMode.Repeated
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppTopAppBar(
            title = stringResource(id = R.string.about_us),
            isBackVisible = true,
            onBack = onNavUp
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CardMediumCorner (
                modifier = Modifier.padding(16.dp)
            ){
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brush)
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        AboutBehsaz()
                        AboutArat()

                    }
                }
            }
        }
    }
}

@Composable
fun AboutBehsaz() {
    val configuration = LocalConfiguration.current
    val logoSize = 0.4 * configuration.screenWidthDp.dp

    TextTitleMedium(text = stringResource(id = R.string.app_description_name),modifier = Modifier.padding(bottom = 16.dp))
    TextTitleSmall(text = stringResource(id = R.string.version_number))
    Image(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .size(logoSize),
        painter = painterResource(R.drawable.behsaz_logo),
        contentDescription = null,
        alignment = Alignment.Center
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AboutRowItem(titleId = R.string.behsaz_phone, iconId = R.mipmap.ic_phone_blue)
        AboutRowItem(titleId = R.string.behsaz_website, iconId = R.mipmap.ic_web_blue, isTextBtn = true)
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        TextLabelSmall(text = stringResource(id = R.string.app_about))
    }
}

@Composable
fun AboutArat() {
    val configuration = LocalConfiguration.current
    val logoWidth = 0.5 * configuration.screenWidthDp.dp
    val logoHeight = 0.46 * 0.5 * configuration.screenWidthDp.dp

    Spacer(modifier = Modifier.height(24.dp))
    TextTitleMediumPrimary(text = stringResource(id = R.string.design_and_developed))
    Image(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .width(logoWidth)
            .height(logoHeight),
        painter = painterResource(R.drawable.arat_logo),
        contentDescription = null,
        alignment = Alignment.Center
    )
    TextTitleMedium(text = stringResource(id = R.string.company_name))
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        TextLabelSmall(text = stringResource(id = R.string.if_you_follow_quality),modifier = Modifier.padding(top = 16.dp))
        TextLabelSmall(text = stringResource(id = R.string.we_are_ready_for_this))
        TextLabelSmall(text = stringResource(id = R.string.because_of_make_that_is_for_us))
    }
    Spacer(modifier = Modifier.height(16.dp))
    AboutRowItem(titleId = R.string.company_phone, iconId = R.mipmap.ic_phone_blue)
    AboutRowItem(
        titleId = R.string.company_website,
        iconId = R.mipmap.ic_web_blue,
        isTextBtn = true
    )
}

@Composable
fun AboutRowItem(
    titleId: Int,
    iconId: Int,
    isTextBtn: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = null
        )
        if (isTextBtn) {
            val context = LocalContext.current
            val url = stringResource(id = titleId)
            val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("http://$url")) }
            TextButton(onClick = { context.startActivity(intent) }) {
                TextTitleSmall(text = url)
            }
        } else {
            TextTitleSmall(text = stringResource(id = titleId))
        }
    }
}