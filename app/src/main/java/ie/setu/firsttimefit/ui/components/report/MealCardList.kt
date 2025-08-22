package ie.setu.firsttimefit.ui.components.report


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.ui.components.report.MealCard
import ie.setu.firsttimefit.data.fakeMeals
import java.text.DateFormat

@Composable
internal fun MealCardList(
    meals: SnapshotStateList<MealModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = meals,
            key = { meal -> meal.id }
        ) { meal ->
            MealCard(
                mealType = meal.mealType,
                calories = meal.calories,
                description = meal.description,
                dateAdded = DateFormat.getDateTimeInstance().format(meal.dateAdded)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealCardListPreview() {
    MealCardList(
        meals = fakeMeals.toMutableStateList()
    )
}