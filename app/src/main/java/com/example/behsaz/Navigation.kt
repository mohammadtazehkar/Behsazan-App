package com.example.behsaz

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.behsaz.data.models.myAddress.MyAddressListData
import com.example.behsaz.presentation.viewmodels.SharedViewModel
import com.example.behsaz.ui.screens.*
import com.example.behsaz.utils.ArgumentKeys.ADDRESS
import com.example.behsaz.utils.ArgumentKeys.ADDRESS_ID
import com.example.behsaz.utils.ArgumentKeys.ADDRESS_TITLE
import com.example.behsaz.utils.ArgumentKeys.CATEGORY_ID
import com.example.behsaz.utils.ArgumentKeys.CATEGORY_TITLE
import com.example.behsaz.utils.ArgumentKeys.FOR_WHAT
import com.example.behsaz.utils.ArgumentKeys.LATITUDE
import com.example.behsaz.utils.ArgumentKeys.LONGITUDE
import com.example.behsaz.utils.Constants.FOR_ADD_LOCATION
import com.example.behsaz.utils.Constants.FOR_VIEW_LOCATION
import com.example.behsaz.utils.Destinations.ABOUT_US_SCREEN
import com.example.behsaz.utils.Destinations.ADD_ADDRESS_SCREEN
import com.example.behsaz.utils.Destinations.ADD_SERVICE_SCREEN
import com.example.behsaz.utils.Destinations.HOME_SCREEN
import com.example.behsaz.utils.Destinations.MAP_SCREEN
import com.example.behsaz.utils.Destinations.MESSAGES_SCREEN
import com.example.behsaz.utils.Destinations.MY_ADDRESSES_SCREEN
import com.example.behsaz.utils.Destinations.MY_SERVICES_SCREEN
import com.example.behsaz.utils.Destinations.PROFILE_SCREEN
import com.example.behsaz.utils.Destinations.RULES_SCREEN
import com.example.behsaz.utils.Destinations.SIGN_IN_SCREEN
import com.example.behsaz.utils.Destinations.SIGN_UP_SCREEN
import com.example.behsaz.utils.Destinations.SPLASH_SCREEN

@Composable
fun BehsazNavHost(
    sharedViewModel: SharedViewModel,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN,
//        startDestination = MAP_SCREEN,
    ) {

        composable(
            route = SPLASH_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            SplashScreen (
                onNavigateToSignIn = {
                    navController.navigate(SIGN_IN_SCREEN) {
                        popUpTo(SPLASH_SCREEN) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(HOME_SCREEN) {
                        popUpTo(SPLASH_SCREEN) {
                            inclusive = true
                        }
                    }
                }
                //                onNavigateToSignUp = {
//                    navController.navigate(SURVEY_ROUTE)
//                },
//                onNavigateToMain = {
//                    navController.navigate(SURVEY_ROUTE)
//                },
            )
        }
        composable(
            route = SIGN_IN_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            SignInScreen(
                onNavigateToSignUp = {
                    navController.navigate(SIGN_UP_SCREEN)
                },
                onNavigateToHome = {
                    navController.navigate(HOME_SCREEN){
                        popUpTo(SIGN_IN_SCREEN) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = SIGN_UP_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            SignUpScreen(
                onSignUpSubmitted = {
                    navController.navigate(HOME_SCREEN){
                        popUpTo(SIGN_UP_SCREEN) {
                            inclusive = true
                        }
                        popUpTo(SIGN_IN_SCREEN) {
                            inclusive = true
                        }
                    }
                },
                onNavUp = navController::navigateUp,
            )
        }
        composable(
            route = HOME_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            HomeScreen(
                onNavigateToAddService = { catId,catTitle ->
                    navController.navigate("$ADD_SERVICE_SCREEN/$catId/$catTitle")
                },
                onDrawerItemClick = { navigateTo ->
                    if (navigateTo.isNotEmpty()) {
                        navController.navigate(navigateTo)
                    }
                },
            )
        }
        composable(
            route = PROFILE_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            ProfileScreen(
                onExpiredToken = {
                    navController.navigate(SPLASH_SCREEN){
                        popUpTo(PROFILE_SCREEN) {
                            inclusive = true
                        }
                        popUpTo(HOME_SCREEN) {
                            inclusive = true
                        }
                    }
                },
                onNavUp = navController::navigateUp
            )
        }
        composable(
            route = MY_SERVICES_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            MyServiceListScreen (
                onServiceItemClick = {
                    navController.navigate("$ADD_SERVICE_SCREEN/0/ ")
                },
                onNavUp = navController::navigateUp
            )
        }
        composable(
            route = MY_ADDRESSES_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            MyAddressListScreen(
                sharedViewModel = sharedViewModel,
                onAddAddressClick = { navController.navigate("$ADD_ADDRESS_SCREEN/0/ / /0.00/0.00") },
                onEditAddressClick = { addressId,addressTitle,address,latitude,longitude ->

                    navController.navigate("$ADD_ADDRESS_SCREEN/$addressId/$addressTitle/$address/$latitude/$longitude")
                },
                onShowLocation = {
                    navController.navigate("$MAP_SCREEN/$FOR_VIEW_LOCATION")
                },
                onNavUp = navController::navigateUp
            )
        }
        composable(
            route = MESSAGES_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            MessageListScreen (
                onNavUp = navController::navigateUp
            )
        }
        composable(
            route = RULES_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            RulesScreen(
                onNavUp = navController::navigateUp
            )
        }
        composable(
            route = ABOUT_US_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            AboutUsScreen(
                onNavUp = navController::navigateUp
            )
        }
        composable(
            route = "$ADD_ADDRESS_SCREEN/{$ADDRESS_ID}/{$ADDRESS_TITLE}/{$ADDRESS}/{$LATITUDE}/{$LONGITUDE}",
            arguments = listOf(
                navArgument(ADDRESS_ID){
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument(ADDRESS_TITLE){
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(ADDRESS){
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(LATITUDE){
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(LATITUDE){
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {

            val addressId = it.arguments?.getInt(ADDRESS_ID)!!
            val addressTitle = it.arguments?.getString(ADDRESS_TITLE)!!
            val address = it.arguments?.getString(ADDRESS)!!
            val latitude = it.arguments?.getString(LATITUDE)!!
            val longitude = it.arguments?.getString(LONGITUDE)!!

            AddAddressScreen (
                sharedViewModel = sharedViewModel,
                onNavUp = navController::navigateUp,
                onSelectLocation = {forWhat ->
                    navController.navigate("$MAP_SCREEN/$forWhat")
                },
                item =
                    if (addressId != 0){
                        MyAddressListData(addressId,addressTitle,address,"$latitude,$longitude")
                    }
                    else{
                        MyAddressListData(0,"","","0.00,0.00")
                    }
            )
        }
        composable(
            route = "$ADD_SERVICE_SCREEN/{$CATEGORY_ID}/{$CATEGORY_TITLE}",
            arguments = listOf(
                navArgument(CATEGORY_ID){
                    type = NavType.IntType
                    defaultValue = 0
            },
                navArgument(CATEGORY_TITLE){
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {

            val categoryId = it.arguments?.getInt(CATEGORY_ID)!!
            val categoryTitle = it.arguments?.getString(CATEGORY_TITLE)!!
            AddServiceScreen(
                sharedViewModel = sharedViewModel,
                categoryId = categoryId,
                categoryTitle = categoryTitle,
                onSelectLocation = {
                    navController.navigate("$MAP_SCREEN/$FOR_ADD_LOCATION")
                },
                onShowLocation = {
                    navController.navigate("$MAP_SCREEN/$FOR_VIEW_LOCATION")
                },
                onNavUp = navController::navigateUp
            )
        }
        composable(
//            route = "$MAP_SCREEN/{$CATEGORY_ID}/{$CATEGORY_TITLE}/{$ADDRESS_ID}/{$ADDRESS_TITLE}/{$ADDRESS}/{$LATITUDE}/{$LONGITUDE}/{$DESCRIPTION}",
            route = "$MAP_SCREEN/{$FOR_WHAT}",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
//            MapScreen()
//            MapRoute()
//            val categoryId = it.arguments?.getInt(CATEGORY_ID)
            val forWhat = it.arguments?.getString(FOR_WHAT)!!
//            val addressId = it.arguments?.getInt(ADDRESS_ID)
//            val addressTitle = it.arguments?.getString(ADDRESS_TITLE)
//            val address = it.arguments?.getString(ADDRESS)
//            val latitude = it.arguments?.getDouble(LATITUDE)
//            val longitude = it.arguments?.getDouble(LONGITUDE)
//            val description = it.arguments?.getString(DESCRIPTION)
            MapScreen(
                sharedViewModel = sharedViewModel,
                forWhat = forWhat,
                onSubmitLocation = navController::navigateUp,
                onNavUp = navController::navigateUp
            )
        }
    }
}