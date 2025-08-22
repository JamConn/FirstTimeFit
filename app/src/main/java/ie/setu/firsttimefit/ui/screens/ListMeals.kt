package ie.setu.firsttimefit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.data.fakeMeals
import ie.setu.firsttimefit.ui.components.report.MealCardList
import ie.setu.firsttimefit.ui.components.report.ReportText
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme

@Composable
fun ListMealsScreen(
    modifier: Modifier = Modifier,
    meals: SnapshotStateList<MealModel>
) {
    Column {
        Column(
            modifier = modifier.padding(
                top = 48.dp,
                start = 24.dp,
                end = 24.dp
            )
        ) {
            ReportText()
            MealCardList(
                meals = meals
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListMealsScreenPreview() {
    FirstTimeFitTheme {
        ListMealsScreen(
            modifier = Modifier,
            meals = fakeMeals.toMutableStateList()
        )
    }
}