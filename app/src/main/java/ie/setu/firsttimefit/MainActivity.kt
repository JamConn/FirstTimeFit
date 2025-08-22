package ie.setu.firsttimefit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.data.fakeMeals
import ie.setu.firsttimefit.ui.components.general.MenuItem
import ie.setu.firsttimefit.ui.screens.AddMealScreen
import ie.setu.firsttimefit.ui.screens.ListMealsScreen
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme

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
fun FirstTimeFitApp(modifier: Modifier = Modifier) {
    val meals = remember { mutableStateListOf<MealModel>() }
    var selectedMenuItem by remember { mutableStateOf<MenuItem?>(MenuItem.AddMeal) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    if (selectedMenuItem == MenuItem.AddMeal) {
                        IconButton(onClick = { selectedMenuItem = MenuItem.Report }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.List,
                                contentDescription = "Show Meals",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    } else {
                        IconButton(onClick = { selectedMenuItem = MenuItem.AddMeal }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add Meal",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            when (selectedMenuItem) {
                MenuItem.AddMeal -> AddMealScreen(
                    modifier = Modifier.padding(paddingValues),
                    meals = meals
                )
                MenuItem.Report -> ListMealsScreen(
                    modifier = Modifier.padding(paddingValues),
                    meals = meals
                )
                else -> {}
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    FirstTimeFitTheme {
        FirstTimeFitApp(modifier = Modifier)
    }
}