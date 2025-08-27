package ie.setu.firsttimefit.ui.screens.health

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.firsttimefit.ui.components.general.HealthConnectManager
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit

@Composable
fun HealthScreen(viewModel: HealthViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var steps by remember { mutableStateOf(0L) }
    var calories by remember { mutableStateOf(0.0) }
    var permissionsGranted by remember { mutableStateOf(false) }

    var distanceMeters by remember { mutableStateOf(0.0) }
    var avgHeartRate by remember { mutableStateOf(0.0) }


    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        contract = HealthConnectManager.requestPermissionsActivityContract()
    ) { granted: Set<String> ->
        if (granted.containsAll(HealthConnectManager.PERMISSIONS)) {
            permissionsGranted = true
            coroutineScope.launch {
                val now = Instant.now()
                val startOfDay = now.truncatedTo(ChronoUnit.DAYS)
                val p = HealthConnectManager.getHealthDataForToday(context)
                steps = p.first
                calories = p.second
                distanceMeters = HealthConnectManager.readDistanceMeters(context, startOfDay, now)
                avgHeartRate = HealthConnectManager.readAverageHeartRate(context, startOfDay, now)
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
            val now = Instant.now()
            val startOfDay = now.truncatedTo(ChronoUnit.DAYS)
            val p = HealthConnectManager.getHealthDataForToday(context)
            steps = p.first
            calories = p.second
            distanceMeters = HealthConnectManager.readDistanceMeters(context, startOfDay, now)
            avgHeartRate = HealthConnectManager.readAverageHeartRate(context, startOfDay, now)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title area
        Text(
            text = "Today’s Health Stats",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (!permissionsGranted) {
            Text(
                "We need access to your health data to show steps, calories, distance & heart rate.",
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Button(
                onClick = { requestPermissionsLauncher.launch(HealthConnectManager.PERMISSIONS) },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Grant permission")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "If you've already granted permissions in Health Connect, try restarting the app.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            // cards
            StatCard(
                icon = Icons.AutoMirrored.Filled.DirectionsWalk,
                label = "Steps",
                value = steps.toString()
            )
            StatCard(
                icon = Icons.Default.LocalFireDepartment,
                label = "Calories",
                value = "${"%.1f".format(calories)} kcal"
            )
            StatCard(
                icon = Icons.Default.Place,
                label = "Distance",
                value = "${"%.2f".format(distanceMeters / 1000.0)} km"
            )
            StatCard(
                icon = Icons.Default.Favorite,
                label = "Avg Heart Rate",
                value = if (avgHeartRate > 0.0) "${"%.0f".format(avgHeartRate)} bpm" else "—"
            )
        }
    }
}

@Composable
private fun StatCard(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(text = label, style = MaterialTheme.typography.bodyMedium)
                Text(text = value, style = MaterialTheme.typography.titleMedium, fontSize = 18.sp)
            }
        }
    }
}