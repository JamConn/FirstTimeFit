package ie.setu.firsttimefit.ui.components.diet

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.stringResource
import ie.setu.firsttimefit.data.model.MealModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.firsttimefit.data.model.fakeMeals
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme
import ie.setu.firsttimefit.R
import ie.setu.firsttimefit.ui.screens.list.ListMealsViewModel
import ie.setu.firsttimefit.ui.screens.meal.AddMealViewModel
import timber.log.Timber

@Composable
fun AddMealButton(
    modifier: Modifier = Modifier,
    meal: MealModel,
    addMealViewModel: AddMealViewModel = hiltViewModel(),
    listMealsViewModel: ListMealsViewModel = hiltViewModel(),
    onTotalCaloriesChange: (Int) -> Unit
) {
    var totalCalories by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val calorieLimit = 2000
    val message = "Warning: Total calories exceeded $calorieLimit!"
    val meals = listMealsViewModel.uiMeals.collectAsState().value
    val currentTotal = meals.sumOf { it.calories }

    Row {
        Button(
            onClick = {
                totalCalories += meal.calories
                onTotalCaloriesChange(totalCalories)
                addMealViewModel.insert(meal)


                if (totalCalories > calorieLimit) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                Timber.i("Meal added: $meal")
                Timber.i("All Meals: ${meals.toList()}")
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Meal", modifier = Modifier.size(24.dp))
            Spacer(modifier.width(4.dp))
            Text(
                text = stringResource(R.string.dietButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(modifier.weight(1f))

        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)) {
                    append(stringResource(R.string.calories) + " ")
                }
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.secondary)) {
                    append(totalCalories.toString())
                }
            }
        )
    }
}
@Composable
private fun PreviewableAddMealButton(
    modifier: Modifier = Modifier,
    meal: MealModel,
    previewMeals: SnapshotStateList<MealModel>,
    onTotalCaloriesChange: (Int) -> Unit
) {
    val context = LocalContext.current
    val calorieLimit = 2000
    val message = "Warning: Total calories exceeded $calorieLimit!"

    var total by remember { mutableIntStateOf(previewMeals.sumOf { it.calories }) }

    Row {
        Button(
            onClick = {
                total += meal.calories
                previewMeals.add(meal)
                onTotalCaloriesChange(total)
                if (total > calorieLimit) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Meal", modifier = Modifier.size(24.dp))
            Spacer(modifier.width(4.dp))
            Text(
                text = stringResource(R.string.dietButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(modifier.weight(1f))

        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)) {
                    append(stringResource(R.string.calories) + " ")
                }
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.secondary)) {
                    append(total.toString())
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddMealButtonPreview() {
    FirstTimeFitTheme {
        PreviewableAddMealButton(
            modifier = Modifier,
            meal = MealModel(),
            previewMeals = fakeMeals.toMutableStateList(),
            onTotalCaloriesChange = {}
        )
    }
}
