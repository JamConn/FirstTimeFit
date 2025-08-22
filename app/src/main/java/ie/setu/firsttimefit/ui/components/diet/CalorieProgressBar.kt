package ie.setu.firsttimefit.ui.components.diet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme

@Composable
fun CalorieProgressBar(
    modifier: Modifier = Modifier,
    currentCalories: Int,
    maxCalories: Int = 5000
) {
    var progress by remember { mutableFloatStateOf(0f) }
    progress = currentCalories / maxCalories.toFloat()

    LinearProgressIndicator(
    progress = { progress },
    modifier = modifier
                .padding(top = 24.dp, bottom = 24.dp)
                .height(8.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
    color = MaterialTheme.colorScheme.secondary,
    trackColor = ProgressIndicatorDefaults.linearTrackColor,
    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
    )
}

@Preview(showBackground = true)
@Composable
fun CalorieProgressBarPreview() {
    FirstTimeFitTheme {
        CalorieProgressBar(currentCalories = 1000)
    }
}