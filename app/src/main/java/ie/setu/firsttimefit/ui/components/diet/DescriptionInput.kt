package ie.setu.firsttimefit.ui.components.diet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme
import ie.setu.firsttimefit.R

@Composable
fun DescriptionInput(
    modifier: Modifier = Modifier,
    onDescriptionChange: (String) -> Unit
) {

    var description by remember { mutableStateOf("") }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        maxLines = 2,
        value = description,
        onValueChange = {
            description = it
            onDescriptionChange(description)
        },
        modifier = modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.enter_message)) } // Reusing enter_message string
    )
}

@Preview(showBackground = true)
@Composable
fun DescriptionInputPreview() {
    FirstTimeFitTheme {
        DescriptionInput(
            Modifier,
            onDescriptionChange = {}
        )
    }
}