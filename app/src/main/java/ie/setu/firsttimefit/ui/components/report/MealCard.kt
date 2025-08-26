package ie.setu.firsttimefit.ui.components.report

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ie.setu.firsttimefit.R
import java.text.DateFormat

@Composable
fun MealCard(
    mealType: String,
    calories: Int,
    description: String,
    dateAdded: String,
    onClickDelete: () -> Unit,
    onClickMealDetails: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 2.dp)
    ) {
        MealCardContent(
            mealType = mealType,
            calories = calories,
            description = description,
            dateAdded = dateAdded,
            onClickDelete = onClickDelete,
            onClickMealDetails = onClickMealDetails
        )
    }
}

@Composable
private fun MealCardContent(
    mealType: String,
    calories: Int,
    description: String,
    dateAdded: String,
    onClickDelete: () -> Unit,
    onClickMealDetails: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(2.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.tertiary
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Business,
                    contentDescription = "Meal Type",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = mealType,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "$calories cal",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            Text(
                text = "Added $dateAdded",
                style = MaterialTheme.typography.labelSmall
            )

            if (expanded) {
                Text(modifier = Modifier.padding(vertical = 16.dp), text = description)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FilledTonalButton(onClick = onClickMealDetails) {
                        Text(text = "Show More")
                    }

                    FilledTonalIconButton(onClick = { showDeleteConfirmDialog = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Meal")
                    }

                    if (showDeleteConfirmDialog) {
                        showDeleteAlert(
                            onDismiss = { showDeleteConfirmDialog = false },
                            onDelete = {
                                showDeleteConfirmDialog = false
                                onClickDelete()
                            }
                        )
                    }
                }
            }
        }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Composable
fun showDeleteAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.confirm_delete)) },
        text = { Text(stringResource(id = R.string.confirm_delete_message)) },
        confirmButton = {
            Button(onClick = { onDelete() }) { Text("Yes") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("No") }
        }
    )
}