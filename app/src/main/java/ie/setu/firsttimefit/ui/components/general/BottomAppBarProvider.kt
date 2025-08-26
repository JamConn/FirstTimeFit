package ie.setu.firsttimefit.ui.components.general

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ie.setu.firsttimefit.navigation.AppDestination
import ie.setu.firsttimefit.navigation.bottomAppBarDestinations
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme

@Composable
fun BottomAppBarProvider(
    navController: NavHostController,
    currentScreen: AppDestination,
    userDestinations: List<AppDestination>
) {
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        bottomAppBarDestinations.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = navigationItem == currentScreen,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.Black
                ),
                label = { Text(text = navigationItem.label) },
                icon = { Icon(navigationItem.icon, contentDescription = navigationItem.label) },
                onClick = {
                    navigationSelectedItem = index
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomAppBarPreview() {
    FirstTimeFitTheme {
        BottomAppBarProvider(
            navController = rememberNavController(),
            currentScreen = bottomAppBarDestinations.get(1),
            bottomAppBarDestinations
        )
    }
}