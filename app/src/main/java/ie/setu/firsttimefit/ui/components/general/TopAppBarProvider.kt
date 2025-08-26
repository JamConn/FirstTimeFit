package ie.setu.firsttimefit.ui.components.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ie.setu.firsttimefit.navigation.AppDestination
import ie.setu.firsttimefit.navigation.AddMeal
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarProvider(
    navController: NavController,
    currentScreen: AppDestination,
    canNavigateBack: Boolean,
    email: String,
    name: String,
    navigateUp: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = currentScreen.label,
                    color = Color.White
                )
                Row {
                    if (name.isNotEmpty())
                        Text(
                            text = name,
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    if (email.isNotEmpty())
                        Text(
                            text = " ($email)",
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            } else {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        },
        actions = { DropDownMenu(navController = navController) }
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    FirstTimeFitTheme {
        TopAppBarProvider(
            navController = rememberNavController(),
            currentScreen = AddMeal,
            canNavigateBack = true,
            email = "dave@gmail.com",
            name = "userName!!"
        )
    }
}