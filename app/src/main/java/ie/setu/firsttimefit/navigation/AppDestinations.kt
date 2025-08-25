package ie.setu.firsttimefit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Details
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object ListMeals : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
    override val label = "List Meals"
    override val route = "list_meals"
}

object AddMeal : AppDestination {
    override val icon = Icons.Filled.Add
    override val label = "Add Meal"
    override val route = "add_meal"
}

object About : AppDestination {
    override val icon = Icons.Filled.Info
    override val label = "About"
    override val route = "about"
}

object Details : AppDestination {
    override val icon = Icons.Filled.Details
    override val label = "Details"
    const val idArg = "id"
    override val route = "details/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType }
    )
}
val bottomAppBarDestinations = listOf(AddMeal, ListMeals, About)
val allDestinations = listOf(ListMeals, AddMeal, About, Details)