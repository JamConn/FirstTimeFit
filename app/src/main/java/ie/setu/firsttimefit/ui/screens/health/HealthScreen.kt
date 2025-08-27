package ie.setu.firsttimefit.ui.screens.health

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.firsttimefit.ui.components.general.HealthConnectManager
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import kotlinx.coroutines.launch

@Composable
fun HealthScreen(viewModel: HealthViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var steps by remember { mutableStateOf(0L) }
    var calories by remember { mutableStateOf(0.0) }
    var permissionsGranted by remember { mutableStateOf(false) }


    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        contract = HealthConnectManager.requestPermissionsActivityContract()
    ) { granted: Set<String> ->
        if (granted.containsAll(HealthConnectManager.PERMISSIONS)) {
            permissionsGranted = true
            coroutineScope.launch {
                val data = HealthConnectManager.getHealthDataForToday(context)
                steps = data.first
                calories = data.second
            }
        } else {
            Toast.makeText(
                context,
                "Permissions denied. Cannot load health data.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(Unit) {
        permissionsGranted = HealthConnectManager.hasAllPermissions(context)
        if (permissionsGranted) {
            val data = HealthConnectManager.getHealthDataForToday(context)
            steps = data.first
            calories = data.second
        }
    }

    Column(modifier = Modifier.padding(24.dp)) {
        if (!permissionsGranted) {
            Text(
                "We need access to your health data to show steps & calories.",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(onClick = { requestPermissionsLauncher.launch(HealthConnectManager.PERMISSIONS) }) {
                Text("Grant permission")
            }
        } else {
            Text("Steps Today: $steps")
            Text("Calories Burned: ${"%.2f".format(calories)} kcal")
        }
    }
}