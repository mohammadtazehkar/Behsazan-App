package com.example.behsaz.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.behsaz.R
import com.example.behsaz.ui.components.CardMediumCorner
import com.example.behsaz.ui.components.TextTitleSmall
import com.example.behsaz.ui.components.TextTitleSmallPrimary
import com.example.behsaz.utils.ClickHelper

@Composable
fun LogoutDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
){
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        CardMediumCorner {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onPrimary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ){
                    TextTitleSmall(
                        text = stringResource(id = R.string.sure_about_logout),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Image(
                        painter = painterResource(id = R.mipmap.ic_exit_blue),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Divider(modifier = Modifier.height(1.dp))
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                ){
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .clickable { ClickHelper.getInstance().clickOnce { onDismissRequest() } },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextTitleSmallPrimary(
                            text = stringResource(id = R.string.cancel),
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                    Divider(modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight())
                    Column(
                        modifier = Modifier
                            .weight(0.5f)
                            .clickable { ClickHelper.getInstance().clickOnce { onConfirmation() }} ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextTitleSmallPrimary(
                            text = stringResource(id = R.string.ok),
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }
            }
        }
    }
}