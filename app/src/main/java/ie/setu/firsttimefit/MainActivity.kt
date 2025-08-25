package ie.setu.firsttimefit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.data.fakeMeals
import ie.setu.firsttimefit.navigation.*
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme
import ie.setu.firsttimefit.ui.components.general.BottomAppBarProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FirstTimeFitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FirstTimeFitApp(modifier = Modifier)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstTimeFitApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {


    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen =
        allDestinations.find { it.route == currentDestination?.route } ?: ListMeals

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarProvider(
                currentScreen = currentBottomScreen,
                canNavigateBack = navController.previousBackStackEntry != null
            ) {
                navController.navigateUp()
            }
        },
        content = { paddingValues ->
            NavHostProvider(
                modifier = modifier,
                navController = navController,
                paddingValues = paddingValues,
            )
        },
        bottomBar = {
            BottomAppBarProvider(
                navController = navController,
                currentScreen = currentBottomScreen
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarProvider(
    currentScreen: AppDestination,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = currentScreen.label,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Button",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        actions = { /* */ }
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    FirstTimeFitTheme {
        TopAppBarProvider(
            currentScreen = AddMeal,
            canNavigateBack = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    FirstTimeFitTheme {
        FirstTimeFitApp(modifier = Modifier)
    }
}