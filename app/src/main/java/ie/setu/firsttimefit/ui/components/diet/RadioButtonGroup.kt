package ie.setu.firsttimefit.ui.components.diet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme
import ie.setu.firsttimefit.R

@Composable
fun RadioButtonGroup(
    modifier: Modifier = Modifier,
    onDietTypeChange: (String) -> Unit
) {

    val radioOptions = listOf(
        stringResource(R.string.meat),
        stringResource(R.string.meat_free),
        stringResource(R.string.vegan)
    )
    var selectedDiet by remember { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        radioOptions.forEach { dietText ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (dietText == selectedDiet),
                    onClick = {
                        selectedDiet = dietText
                        onDietTypeChange(selectedDiet)
                    }
                )
                Text(
                    text = dietText,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonPreview() {
    FirstTimeFitTheme {
        RadioButtonGroup(
            Modifier,
            onDietTypeChange = {}
        )
    }
}