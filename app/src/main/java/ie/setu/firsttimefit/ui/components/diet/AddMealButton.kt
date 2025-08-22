package ie.setu.firsttimefit.ui.components.diet

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
import ie.setu.firsttimefit.data.MealModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.withStyle
import ie.setu.firsttimefit.data.fakeMeals
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme
import ie.setu.firsttimefit.R
import timber.log.Timber

@Composable
fun AddMealButton(
    modifier: Modifier = Modifier,
    meal: MealModel,
    meals: SnapshotStateList<MealModel>,
    onTotalCaloriesChange: (Int) -> Unit
) {
    var totalCalories by remember { mutableIntStateOf(0) }

    Row {
        Button(
            onClick = {
                totalCalories += meal.calories
                onTotalCaloriesChange(totalCalories)
                meals.add(meal)
                Timber.i("Meal added: $meal")
                Timber.i("All Meals: ${meals.toList()}")
            },
            elevation = ButtonDefaults.buttonElevation(20.dp) // matched lecturer
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Meal")
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
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                ) {
                    append("Total Calories: ")
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    append(totalCalories.toString())
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddMealButtonPreview() {
    FirstTimeFitTheme {
        AddMealButton(
            Modifier,
            MealModel(), // placeholder for preview
            meals = fakeMeals.toMutableStateList(),
            onTotalCaloriesChange = {}

        )
    }
}