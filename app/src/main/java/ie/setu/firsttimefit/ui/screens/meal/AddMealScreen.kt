package ie.setu.firsttimefit.ui.screens.meal

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.firsttimefit.data.model.MealModel
import ie.setu.firsttimefit.data.model.fakeMeals
import ie.setu.firsttimefit.ui.components.diet.AddMealButton
import ie.setu.firsttimefit.ui.components.diet.CaloriePicker
import ie.setu.firsttimefit.ui.components.diet.DescriptionInput
import ie.setu.firsttimefit.ui.components.diet.RadioButtonGroup
import ie.setu.firsttimefit.ui.components.diet.WelcomeText
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.firsttimefit.ui.components.diet.CalorieProgressBar
import ie.setu.firsttimefit.ui.screens.list.ListMealsViewModel

@Composable
fun AddMealScreen(
    modifier: Modifier = Modifier,
    listMealsViewModel: ListMealsViewModel = hiltViewModel()
) {
    var mealType by remember { mutableStateOf("Meat") }
    var calories by remember { mutableIntStateOf(100) }
    var description by remember { mutableStateOf("No notes") }
    val meals = listMealsViewModel.uiMeals.collectAsState().value
    var totalCalories by remember { mutableIntStateOf(meals.sumOf { it.calories }) }

    totalCalories = meals.sumOf { it.calories }


    Column {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, bottom = 72.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            WelcomeText()

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButtonGroup(
                    modifier = Modifier.weight(1f),
                    onDietTypeChange = { mealType = it }
                )
                Spacer(modifier = Modifier.width(16.dp))
                CaloriePicker(
                    onCaloriesChange = { calories = it }
                )
            }

            DescriptionInput(
                modifier = Modifier.fillMaxWidth(),
                onDescriptionChange = { description = it }
            )

            CalorieProgressBar(
                modifier = Modifier.fillMaxWidth(),
                currentCalories = totalCalories
            )

            AddMealButton(
                meal = MealModel(
                    mealType = mealType,
                    calories = calories,
                    description = description
                ),

                onTotalCaloriesChange = { totalCalories = it }
            )
        }
    }
}

@Composable
fun PreviewAddMealScreen(
    modifier: Modifier = Modifier,
    meals: SnapshotStateList<MealModel>
) {
    var mealType by remember { mutableStateOf("Meat") }
    var calories by remember { mutableIntStateOf(100) }
    var description by remember { mutableStateOf("No notes") }
    var totalCalories = meals.sumOf { it.calories }
    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 72.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            WelcomeText()

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButtonGroup(
                    modifier = Modifier.weight(1f),
                    onDietTypeChange = { mealType = it }
                )
                Spacer(modifier = Modifier.width(16.dp))
                CaloriePicker(
                    onCaloriesChange = { calories = it }
                )
            }

            DescriptionInput(
                modifier = Modifier.fillMaxWidth(),
                onDescriptionChange = { description = it }
            )

            CalorieProgressBar(
                modifier = Modifier.fillMaxWidth(),
                currentCalories = totalCalories
            )

             AddMealButton(
                meal = MealModel(
                    mealType = mealType,
                    calories = calories,
                    description = description
                ),
                onTotalCaloriesChange = { totalCalories = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddMealScreenPreview() {
    FirstTimeFitTheme {
        PreviewAddMealScreen(
            modifier = Modifier,
            meals = fakeMeals.toMutableStateList()
        )
    }
}