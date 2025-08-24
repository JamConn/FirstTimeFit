package ie.setu.firsttimefit.ui.components.report

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.firsttimefit.data.MealModel
import java.text.DateFormat
import java.util.Date
import ie.setu.firsttimefit.R

@Composable
fun MealCard(
    mealType: String,
    calories: Int,
    description: String,
    dateAdded: String,
    onClickDelete: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
    ) {
        MealCardContent(mealType, calories, description, dateAdded, onClickDelete = onClickDelete)
    }
}

@Composable
private fun MealCardContent(
    mealType: String,
    calories: Int,
    description: String,
    dateAdded: String,
    onClickDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Business,
                    contentDescription = "Meal Status",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
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


                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalIconButton(
                        onClick = { showDeleteConfirmDialog = true }
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Meal")
                    }
                    FilledTonalIconButton(
                        onClick = { /* Update afterf*/ }
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit Meal")
                    }
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
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) "Show less" else "Show more"
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

@Preview
@Composable
fun MealCardPreview() {
    MealCard(
        mealType = "Meat Free",
        calories = 250,
        description = "Grilled vegetables with quinoa",
        dateAdded = DateFormat.getDateTimeInstance().format(Date()),
        onClickDelete = {}
    )
}