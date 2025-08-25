package ie.setu.firsttimefit.ui.screens.list

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun ListMealsScreen(
    modifier: Modifier = Modifier,
    onClickMealDetails: (Int) -> Unit,
    listMealsViewModel: ListMealsViewModel = hiltViewModel()
) {

    val meals = listMealsViewModel.uiMeals.collectAsState().value

    if (meals.isEmpty()) {
        Centre(Modifier.fillMaxSize()) {
            Text(
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center,
                text = stringResource(R.string.empty_list)
            )
        }
    } else {
        Column {
            Column(
                modifier = modifier.padding(
                    start = 24.dp,
                    end = 24.dp
                ),
            ) {
                ReportText()
                MealCardList(meals = meals,onClickMealDetails = onClickMealDetails, onDeleteMeal = { meal -> listMealsViewModel.deleteMeal(meal) })

            }
        }
    }
}

@Composable
fun PreviewListMealsScreen(
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
                text = stringResource(R.string.empty_list)
            )
        }
    } else {
        Column {
            Column(
                modifier = modifier.padding(
                    start = 24.dp,
                    end = 24.dp
                ),
            ) {
                ReportText()
                MealCardList(meals = meals, onClickMealDetails = {},onDeleteMeal = {})

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListMealsScreenPreview() {
    FirstTimeFitTheme {
        PreviewListMealsScreen(
            modifier = Modifier,
            meals = fakeMeals.toMutableStateList()
        )
    }
}