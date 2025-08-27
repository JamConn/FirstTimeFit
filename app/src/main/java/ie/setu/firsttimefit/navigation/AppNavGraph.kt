package ie.setu.firsttimefit.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.firsttimefit.data.model.MealModel
import ie.setu.firsttimefit.ui.components.general.HealthConnectManager
import ie.setu.firsttimefit.ui.screens.meal.AddMealScreen
import ie.setu.firsttimefit.ui.screens.list.ListMealsScreen
import ie.setu.firsttimefit.ui.screens.about.AboutScreen
import ie.setu.firsttimefit.ui.screens.details.DetailsScreen
import ie.setu.firsttimefit.ui.screens.health.HealthScreen
import ie.setu.firsttimefit.ui.screens.home.HomeScreen
import ie.setu.firsttimefit.ui.screens.profile.ProfileScreen
import ie.setu.firsttimefit.ui.screens.login.LoginScreen
import ie.setu.firsttimefit.ui.screens.register.RegisterScreen

@Composable
fun NavHostProvider(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: AppDestination,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {

        composable(route = AddMeal.route) {
            AddMealScreen(modifier = modifier)
        }


        composable(route = ListMeals.route) {
            ListMealsScreen(
                modifier = modifier,
                onClickMealDetails = { mealId: String ->
                    navController.navigateToMealDetails(mealId)
                }
            )
        }


        composable(route = About.route) {
            AboutScreen(modifier = modifier)
        }


        composable(route = Home.route) {
            HomeScreen(modifier = modifier)
        }


        composable(route = Login.route) {
            LoginScreen(
                navController = navController,
                onLogin = { navController.popBackStack() }
            )
        }


        composable(route = Register.route) {
            RegisterScreen(
                navController = navController,
                onRegister = { navController.popBackStack() }
            )
        }


        composable(
            route = Details.route,
            arguments = Details.arguments
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString(Details.idArg)
            if (id != null) {
                DetailsScreen()
            }
        }


        composable(route = Profile.route) {
            ProfileScreen(
                onSignOut = {
                    navController.popBackStack()
                    navController.navigate(Login.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Health.route) {
            HealthScreen()
        }
}}

private fun NavHostController.navigateToMealDetails(mealId: String) {
    this.navigate("details/$mealId")
}