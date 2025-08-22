package ie.setu.firsttimefit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

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


val bottomAppBarDestinations = listOf(AddMeal, ListMeals)
val allDestinations = listOf(ListMeals, AddMeal)