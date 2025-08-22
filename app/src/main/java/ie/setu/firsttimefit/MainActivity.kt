package ie.setu.firsttimefit


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.data.fakeMeals
import ie.setu.firsttimefit.ui.screens.AddMealScreen
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

@Composable
fun FirstTimeFitApp(modifier: Modifier = Modifier) {
    val meals = remember { mutableStateListOf<MealModel>() }

    AddMealScreen(modifier = modifier, meals = meals)
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    FirstTimeFitTheme {
        FirstTimeFitApp(modifier = Modifier)
    }
}