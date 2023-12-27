package com.example.behsaz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.behsaz.R
import com.example.behsaz.data.models.home.CategoryListData
import com.example.behsaz.data.models.home.SlideListData
import com.example.behsaz.presentation.events.HomeEvent
import com.example.behsaz.presentation.viewmodels.HomeViewModel
import com.example.behsaz.ui.components.AppBannerPager
import com.example.behsaz.ui.components.AppDrawer
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.CardColumnMediumCorner
import com.example.behsaz.ui.components.EmptyView
import com.example.behsaz.ui.components.TextTitleMedium
import com.example.behsaz.utils.Destinations
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.ServerConstants.IMAGE_URL
import com.example.behsaz.utils.UIText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen2(
    homeViewModel: HomeViewModel = viewModel(),
    onNavigateToAddService: (Int, String) -> Unit,
    onDrawerItemClick: (String) -> Unit
) {
    val context = LocalContext.current
    val homeState = homeViewModel.homeState.value
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)


    LaunchedEffect(key1 = true) {
        delay(500)  // the delay of 3 seconds
        drawerState.close()
    }
    if (homeState.logoutDialogVisible) {
        LogoutDialog(
            onDismissRequest = {
                homeViewModel.onEvent(HomeEvent.UpdateLogoutDialog)
            },
            onConfirmation = {
                homeViewModel.onEvent(HomeEvent.UpdateLogoutDialog)
                /*TODO doLogout*/
            }
        )
    }

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer { route ->
                if (route == Destinations.LOG_OUT_SCREEN) {
                    homeViewModel.onEvent(HomeEvent.UpdateLogoutDialog)
                } else {
                    onDrawerItemClick(route)
                }
                scope.launch {
                    drawerState.close()
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold { paddingValues ->
            HomeContent(
                modifier = Modifier.padding(paddingValues),
                imageList = homeState.imageList,
                categoryList = homeState.categoryListState,
                onDrawerOpen = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                onServiceItemClick = {item ->
//                    onNavigateToAddService(item.id,item.title.asString(context))
                    onNavigateToAddService(item.id,item.title)
                }
            )
        }
    }

    when (homeState.response) {
        is Resource.Loading -> {
            // Display loading UI
        }
        is Resource.Success -> {
            // Display success UI with data
            homeViewModel.onEvent(HomeEvent.PrepareData)
        }
        is Resource.Error -> {
            // Display error UI with message
        }
    }

}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    imageList : List<SlideListData>,
    categoryList: List<CategoryListData>,
    onServiceItemClick: (CategoryListData) -> Unit,
    onDrawerOpen: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppTopAppBar(
            title = stringResource(id = R.string.app_name),
            isMenuVisible = true,
            onOpenDrawer = onDrawerOpen,
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppBannerPager(
            modifier = Modifier.weight(0.3f),
            images = imageList
        )
        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(horizontal = 16.dp)
        ) {
            if (categoryList.isNotEmpty()) {
                HomeServiceGroupGrid(
                    items = categoryList,
                    onServiceItemClick = onServiceItemClick
                )
            }
            else{
                EmptyView(text = stringResource(id = R.string.empty_category_list))
            }
        }
    }
}

@Composable
fun HomeServiceGroupGrid(
    items: List<CategoryListData>,
    onServiceItemClick: (CategoryListData) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(count = 2),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp), // top and bottom margin between each item
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp), // left and right margin between each item
        contentPadding = PaddingValues(all = 10.dp) // margin for the whole layout
    ) {
        items(count = items.size) { index ->
            HomeServiceGroupGridItem(
                item = items[index],
                onServiceItemClick = onServiceItemClick
            )
        }
    }
}

@Composable
fun HomeServiceGroupGridItem(
    item: CategoryListData,
    onServiceItemClick: (CategoryListData) -> Unit
) {

    CardColumnMediumCorner(
        columnModifier = Modifier
            .fillMaxWidth()
            .clickable { onServiceItemClick(item) },
        columnHorizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = IMAGE_URL + item.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        TextTitleMedium(
//            text = item.title.asString(),
            text = item.title,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

//data class GroupGridItemData(val id: Int, val title: UIText, val imageResourceId: Int)