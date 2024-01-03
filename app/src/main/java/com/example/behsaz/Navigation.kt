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
                onLogoutCompleted = {
                    navController.navigate(SPLASH_SCREEN){
                        popUpTo(HOME_SCREEN) {
                            inclusive = true
                        }
                    }
                }
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
                onExpiredToken = {
                    navController.navigate(SPLASH_SCREEN){
                        popUpTo(MY_SERVICES_SCREEN) {
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
                onExpiredToken = {
                    navController.navigate(SPLASH_SCREEN){
                        popUpTo(MY_ADDRESSES_SCREEN) {
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
            AddAddressScreen (
                sharedViewModel = sharedViewModel,
                onNavUp = navController::navigateUp,
                onSelectLocation = {forWhat ->
                    navController.navigate("$MAP_SCREEN/$forWhat")
                },
                onExpiredToken = {
                    navController.navigate(SPLASH_SCREEN){
                        popUpTo(ADD_ADDRESS_SCREEN) {
                            inclusive = true
                        }
                        popUpTo(MY_ADDRESSES_SCREEN) {
                            inclusive = true
                        }
                        popUpTo(HOME_SCREEN) {
                            inclusive = true
                        }
                    }
                },
                onSuccess = {
                    navController.navigate(MY_ADDRESSES_SCREEN){
                        popUpTo(ADD_ADDRESS_SCREEN) {
                            inclusive = true
                        }
                        popUpTo(MY_ADDRESSES_SCREEN) {
                            inclusive = true
                        }
                    }
                },
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
            AddServiceScreen(
                sharedViewModel = sharedViewModel,
                onSelectLocation = {
                    navController.navigate("$MAP_SCREEN/$FOR_ADD_LOCATION")
                },
                onShowLocation = {
                    navController.navigate("$MAP_SCREEN/$FOR_VIEW_LOCATION")
                },
                onExpiredToken = {
                    navController.navigate(SPLASH_SCREEN){
                        popUpTo(ADD_SERVICE_SCREEN) {
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
            val forWhat = it.arguments?.getString(FOR_WHAT)!!
            MapScreen(
                sharedViewModel = sharedViewModel,
                forWhat = forWhat,
                onSubmitLocation = navController::navigateUp,
                onNavUp = navController::navigateUp
            )
        }
    }
}