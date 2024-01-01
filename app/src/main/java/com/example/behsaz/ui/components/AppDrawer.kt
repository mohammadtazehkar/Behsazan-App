package com.example.behsaz.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.behsaz.R
import com.example.behsaz.ui.models.DrawerItemData
import com.example.behsaz.utils.Destinations.ABOUT_US_SCREEN
import com.example.behsaz.utils.Destinations.LOG_OUT_SCREEN
import com.example.behsaz.utils.Destinations.MESSAGES_SCREEN
import com.example.behsaz.utils.Destinations.MY_ADDRESSES_SCREEN
import com.example.behsaz.utils.Destinations.MY_SERVICES_SCREEN
import com.example.behsaz.utils.Destinations.PROFILE_SCREEN
import com.example.behsaz.utils.Destinations.RULES_SCREEN

private val topItems = listOf(
    DrawerItemData(
        R.string.first_and_last_name,
        R.mipmap.ic_person_label_blue,
    ),
    DrawerItemData(
        R.string.profile,
        R.mipmap.ic_circle_person_blue,
        PROFILE_SCREEN
    )
)
private val middleItems = listOf(
    DrawerItemData(
        R.string.my_services,
        R.mipmap.ic_app_icon,
        MY_SERVICES_SCREEN
    ),
    DrawerItemData(
        R.string.my_addresses,
        R.mipmap.ic_circle_address_blue,
        MY_ADDRESSES_SCREEN
    ),
    DrawerItemData(
        R.string.messages,
        R.mipmap.ic_message_blue,
        MESSAGES_SCREEN
    )
)
private val bottomItems = listOf(
    DrawerItemData(
        R.string.rules,
        R.mipmap.ic_rules_blue,
        RULES_SCREEN
    ),
    DrawerItemData(
        R.string.about_us,
        R.mipmap.ic_info_blue,
        ABOUT_US_SCREEN
    ),
    DrawerItemData(
        R.string.logout,
        R.mipmap.ic_exit_blue,
        LOG_OUT_SCREEN
    )
)


@Composable
fun AppDrawer(
    fullName : String,
    modifier: Modifier = Modifier,
    onDrawerItemClick: (String) -> Unit
) {
    ModalDrawerSheet {
        Column(
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            DrawerSection(
                fullName,
                topItems,
                onDrawerItemClick
            )
            Spacer(modifier = Modifier.height(24.dp))
            DrawerSection(
                items =  middleItems,
                onDrawerItemClick =  onDrawerItemClick
            )
            Spacer(modifier = Modifier.height(24.dp))
            DrawerSection(
                items = bottomItems,
                onDrawerItemClick =  onDrawerItemClick
            )
            Spacer(modifier = Modifier.height(48.dp))
            DrawerFooter()
        }
    }
}

@Composable
fun DrawerSection(
    fullName: String = "",
    items: List<DrawerItemData>,
    onDrawerItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        for (item in items) {
            DrawerSectionItem(
                fullName = fullName,
                iconId = item.imageResourceId,
                titleId = item.titleId,
                route = item.route,
                isFirst = item == items[0] && fullName.isNotEmpty(),
                isLast = item == items[items.lastIndex],
                onDrawerItemClick = onDrawerItemClick
            )
        }
    }
}

@Composable
fun DrawerSectionItem(
    fullName: String,
    iconId: Int,
    titleId: Int,
    route: String,
    isFirst: Boolean,
    isLast: Boolean,
    onDrawerItemClick: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDrawerItemClick(route) },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = CenterVertically
        ) {
            if (isFirst) {
                TextTitleMedium(text = fullName)
            }else {
                TextTitleMedium(text = stringResource(id = titleId))
            }
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .size(40.dp),
                painter = painterResource(id = iconId), contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        if (!isLast) {
            Divider(
                color = MaterialTheme.colorScheme.secondary,
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun DrawerFooter(){
    val configuration = LocalConfiguration.current
    val logoSize = 0.3 * configuration.screenWidthDp.dp

    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically
    ){
        Image(
            modifier = Modifier
                .size(logoSize)
                .padding(16.dp),
            painter = painterResource(R.drawable.behsaz_logo),
            contentDescription = null,
            alignment = Alignment.Center
        )
        Column {
            TextTitleSmall(text = stringResource(id = R.string.app_description_name))
            TextTitleSmall(text = stringResource(id = R.string.behsaz_phone))
            TextTitleSmall(text = stringResource(id = R.string.version_number))
        }
    }
}

