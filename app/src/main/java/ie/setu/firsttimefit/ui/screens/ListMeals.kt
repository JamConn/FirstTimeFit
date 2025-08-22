package ie.setu.firsttimefit.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.firsttimefit.R
import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.data.fakeMeals
import ie.setu.firsttimefit.ui.components.general.Centre
import ie.setu.firsttimefit.ui.components.report.MealCardList
import ie.setu.firsttimefit.ui.components.report.ReportText
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.toMutableStateList

@Composable
fun ListMealsScreen(
    modifier: Modifier = Modifier,
    meals: SnapshotStateList<MealModel>
) {
    if (meals.isEmpty()) {
        Centre(Modifier.fillMaxSize()) {
            Text(
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center,
                text = androidx.compose.ui.res.stringResource(R.string.empty_list)
            )
        }
    } else {
        Column {
            Column(
                modifier = modifier.padding(
                    top = 48.dp,
                    start = 24.dp,
                    end = 24.dp
                ),
            ) {
                ReportText()
                MealCardList(meals = meals)
            }
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