package ie.setu.firsttimefit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Details
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AccountCircle


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
        navArgument(idArg) { type = NavType.StringType }
    )
}

object Home : AppDestination {
    override val icon = Icons.Filled.Home
    override val label = "Home"
    override val route = "home"
}

object Profile : AppDestination {
    override val icon = Icons.Filled.Person
    override val label = "Profile"
    override val route = "profile"
}

object Login : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.Login
    override val label = "Login"
    override val route = "login"
}

object Register : AppDestination {
    override val icon = Icons.Filled.AccountCircle
    override val label = "Register"
    override val route = "register"
}

val bottomAppBarDestinations = listOf(AddMeal, ListMeals, About, Profile)

val userSignedOutDestinations = listOf(Login, Register)

val allDestinations = listOf(ListMeals, AddMeal, About, Details, Home, Profile, Login, Register)