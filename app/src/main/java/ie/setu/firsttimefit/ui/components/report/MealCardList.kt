package ie.setu.firsttimefit.ui.components.report


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.firsttimefit.data.model.MealModel
import ie.setu.firsttimefit.data.model.fakeMeals
import java.text.DateFormat

@Composable
internal fun MealCardList(
    meals: List<MealModel>,
    modifier: Modifier = Modifier,
    onDeleteMeal: (MealModel) -> Unit,
    onClickMealDetails: (String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = meals,
            key = { meal -> meal._id }
        ) { meal ->
            MealCard(
                mealType = meal.mealType,
                calories = meal.calories,
                description = meal.description,
                dateAdded = DateFormat.getDateTimeInstance().format(meal.dateAdded),
                onClickDelete = { onDeleteMeal(meal) },
                onClickMealDetails = { onClickMealDetails(meal._id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealCardListPreview() {
    MealCardList(
        meals = fakeMeals,
        onDeleteMeal = {},
        onClickMealDetails = {}
    )
}