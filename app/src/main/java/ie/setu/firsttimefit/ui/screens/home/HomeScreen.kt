package ie.setu.firsttimefit.ui.screens.home

import android.annotation.SuppressLint

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ie.setu.firsttimefit.navigation.Login
import ie.setu.firsttimefit.navigation.NavHostProvider
import ie.setu.firsttimefit.navigation.ListMeals
import ie.setu.firsttimefit.navigation.allDestinations
import ie.setu.firsttimefit.navigation.bottomAppBarDestinations
import ie.setu.firsttimefit.navigation.userSignedOutDestinations
import ie.setu.firsttimefit.ui.components.general.BottomAppBarProvider
import ie.setu.firsttimefit.ui.components.general.TopAppBarProvider
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               homeViewModel: HomeViewModel = hiltViewModel(),
               navController: NavHostController = rememberNavController(),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen =
        allDestinations.find { it.route == currentDestination?.route } ?: Login

    var startScreen = currentBottomScreen
    val currentScreen =
        allDestinations.find { it.route == currentDestination?.route } ?: ListMeals
    val currentUser = homeViewModel.currentUser
    val isActiveSession = homeViewModel.isAuthenticated()
    val userEmail = if (isActiveSession) currentUser?.email else ""
    val userName = if (isActiveSession) currentUser?.displayName else ""
    val userDestinations = if (!isActiveSession)
        userSignedOutDestinations
    else bottomAppBarDestinations

    if (isActiveSession) startScreen = ListMeals

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBarProvider(
            navController = navController,
            currentScreen = currentBottomScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            email = userEmail!!,
            name = userName!!,
        ) { navController.navigateUp() }
        },
        content = { paddingValues ->
            NavHostProvider(
                modifier = modifier,
                navController = navController,
                startDestination = startScreen,
                paddingValues = paddingValues
            )
        },
        bottomBar = {
            if (currentScreen.route != Login.route) {
                BottomAppBarProvider(
                    navController = navController,
                    currentScreen = currentScreen,
                    userDestinations = bottomAppBarDestinations
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    FirstTimeFitTheme {
        HomeScreen(modifier = Modifier)
    }
}