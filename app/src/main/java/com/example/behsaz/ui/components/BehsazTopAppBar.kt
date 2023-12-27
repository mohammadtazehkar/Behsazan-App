package com.example.behsaz.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.behsaz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    title: String,
    isBackVisible: Boolean = false,
    onBack: () -> Unit = {},
    isMenuVisible: Boolean = false,
    onOpenDrawer: () -> Unit = {},
    isEditVisible: Boolean = false,
    onEditClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CenterAlignedTopAppBar(
            title = {
                TextTitleMedium(text = title)
            },
            navigationIcon = {
                if (isBackVisible) {
                    IconButton(onClick = onBack) {
                        Image(
                            painter = painterResource(id = R.mipmap.ic_back_blue),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                if (isMenuVisible) {
                    IconButton(onClick = onOpenDrawer) {
                        Image(
                            painter = painterResource(id = R.mipmap.ic_menu_blue),
                            contentDescription = null
                        )
                    }
                }
            },
            actions = {
                if (isEditVisible) {
                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier.padding(4.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.mipmap.ic_edit_blue),
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}