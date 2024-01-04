package com.example.behsaz.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.behsaz.R
import com.example.behsaz.data.models.home.CategoryListData
import com.example.behsaz.presentation.events.HomeEvent
import com.example.behsaz.presentation.events.MessageListEvent
import com.example.behsaz.presentation.viewmodels.HomeViewModel
import com.example.behsaz.ui.components.AppBannerPager
import com.example.behsaz.ui.components.AppDrawer
import com.example.behsaz.ui.components.AppSnackBar
import com.example.behsaz.ui.components.AppTopAppBar
import com.example.behsaz.ui.components.CardColumnMediumCorner
import com.example.behsaz.ui.components.EmptyView
import com.example.behsaz.ui.components.ProgressBarDialog
import com.example.behsaz.ui.components.TextTitleMedium
import com.example.behsaz.utils.ClickHelper
import com.example.behsaz.utils.Destinations
import com.example.behsaz.utils.JSonStatusCode
import com.example.behsaz.utils.Resource
import com.example.behsaz.utils.ServerConstants.IMAGE_URL
import com.example.behsaz.utils.UIText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToAddService: (Int, String) -> Unit,
    onLogoutCompleted: () -> Unit,
    onDrawerItemClick: (String) -> Unit
) {
    val context = LocalContext.current
    val homeState = homeViewModel.homeState.value
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeState.isLoading,
        onRefresh = { homeViewModel.onEvent(HomeEvent.GetHomeData) }
    )

    LaunchedEffect(key1 = true) {
        delay(500)  // the delay of 0.5 seconds
        drawerState.close()
    }
    LaunchedEffect(key1 = homeState.response) {
        when (homeState.response) {
            is Resource.Loading -> {
                // Display loading UI
                homeViewModel.onEvent(HomeEvent.UpdateLoading(true))
            }

            is Resource.Success -> {
                // Display success UI with data
                homeViewModel.onEvent(HomeEvent.UpdateLoading(false))
                if (homeState.response.data?.statusCode == JSonStatusCode.SUCCESS) {
                    homeViewModel.onEvent(HomeEvent.PrepareData)
                }
            }

            is Resource.Error -> {
                // Display error UI with message
                homeViewModel.onEvent(HomeEvent.UpdateLoading(false))
                when (homeState.response.data?.statusCode) {
                    JSonStatusCode.INTERNET_CONNECTION -> {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = UIText.StringResource(R.string.not_connection_internet)
                                    .asString(context),
                                actionLabel = UIText.StringResource(R.string.trye_again)
                                    .asString(context),
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                homeViewModel.onEvent(HomeEvent.GetHomeData)
                            }

                            SnackbarResult.Dismissed -> {
                                /* Handle snackbar dismissed */
                                Log.i("mamali", "ssss")
                            }
                        }
                    }

                    JSonStatusCode.SERVER_CONNECTION -> {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = UIText.StringResource(R.string.server_connection_error)
                                    .asString(context),
                                actionLabel = UIText.StringResource(R.string.trye_again)
                                    .asString(context),
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                homeViewModel.onEvent(HomeEvent.GetHomeData)
                            }

                            SnackbarResult.Dismissed -> {
                                /* Handle snackbar dismissed */
                            }
                        }
                    }
                }
            }
        }
    }

    if (homeState.logoutDialogVisible) {
        LogoutDialog(
            onDismissRequest = {
                homeViewModel.onEvent(HomeEvent.UpdateLogoutDialog)
            },
            onConfirmation = {
                homeViewModel.onEvent(
                    HomeEvent.DoLogout(
                        onLogoutComplete = onLogoutCompleted
                    )
                )
                homeViewModel.onEvent(HomeEvent.UpdateLogoutDialog)
            }
        )
    }
    if (homeState.isLoading) {
        ProgressBarDialog(
            onDismissRequest = {
                homeViewModel.onEvent(HomeEvent.UpdateLoading(false))
            }
        )
    }

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                fullName = homeState.fullName,
            ) { route ->
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
        Scaffold(
            topBar = {
                AppTopAppBar(
                    title = stringResource(id = R.string.app_name),
                    isMenuVisible = true,
                    onOpenDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState) {
                    AppSnackBar(it)
                }
            },
        ) { paddingValues ->
            HomeContent(
                modifier = Modifier.padding(paddingValues),
                imageList = homeState.imageList,
                categoryList = homeState.categoryListState,
                onServiceItemClick = { item ->
//                    onNavigateToAddService(item.id,item.title.asString(context))
                    onNavigateToAddService(item.id, item.title)
                },
                isLoading = homeState.isLoading,
                pullRefreshState = pullRefreshState
            )
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
//    imageList : List<SlideListData>,
    imageList: List<String>,
    categoryList: List<CategoryListData>,
    onServiceItemClick: (CategoryListData) -> Unit,
    isLoading: Boolean,
    pullRefreshState: PullRefreshState
) {
    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
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
                } else {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        EmptyView(text = stringResource(id = R.string.empty_category_list))
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
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
            .clickable {
                ClickHelper
                    .getInstance()
                    .clickOnce { onServiceItemClick(item) }
            },
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