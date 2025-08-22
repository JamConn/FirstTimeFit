package ie.setu.firsttimefit.ui.components.diet

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.ListItemPicker
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme

@Composable
fun CaloriePicker(
    onCaloriesChange: (Int) -> Unit
) {
    val possibleValues = listOf("100", "200", "300", "400", "500", "1000")
    var pickerValue by remember { mutableStateOf(possibleValues[0]) }

    ListItemPicker(
        label = { it },
        dividersColor = MaterialTheme.colorScheme.primary,
        textStyle = TextStyle(Color.Black, 20.sp),
        value = pickerValue,
        onValueChange = {
            pickerValue = it
            onCaloriesChange(pickerValue.toInt())
        },
        list = possibleValues
    )
}

@Preview(showBackground = true)
@Composable
fun CaloriePickerPreview() {
    FirstTimeFitTheme {
        CaloriePicker(onCaloriesChange = {})
    }
}
