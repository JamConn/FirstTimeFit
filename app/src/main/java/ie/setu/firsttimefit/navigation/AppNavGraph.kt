package ie.setu.firsttimefit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.ui.screens.meal.AddMealScreen
import ie.setu.firsttimefit.ui.screens.list.ListMealsScreen
import ie.setu.firsttimefit.ui.screens.about.AboutScreen
import ie.setu.firsttimefit.ui.screens.details.DetailsScreen

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = ListMeals.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = AddMeal.route) {
            AddMealScreen(modifier = modifier)
        }
        composable(route = ListMeals.route) {
            ListMealsScreen(modifier = modifier,onClickMealDetails = { mealId: Int -> navController.navigateToMealDetails(mealId)
            })
        }
        composable(
            route = Details.route,
            arguments = Details.arguments
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Details.idArg)
            if (id != null) {
                DetailsScreen()
            }
        }
        composable(route = About.route) {
            AboutScreen(modifier = modifier)
        }
    }
}

private fun NavHostController.navigateToMealDetails(mealId: Int) {
    this.navigate("details/$mealId")
}