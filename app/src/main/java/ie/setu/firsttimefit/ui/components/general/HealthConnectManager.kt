package ie.setu.firsttimefit.ui.components.general

import android.content.Context
import androidx.activity.result.contract.ActivityResultContract
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Energy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import kotlin.random.Random


object HealthConnectManager {

    private const val HEALTH_CONNECT_PACKAGE = "com.google.android.apps.healthdata"
    private const val PERM_READ_STEPS = "android.permission.health.READ_STEPS"
    private const val PERM_READ_TOTAL_CAL = "android.permission.health.READ_TOTAL_CALORIES_BURNED"

    private const val PERM_READ_DISTANCE = "android.permission.health.READ_DISTANCE"
    private const val PERM_READ_HEARTRATE = "android.permission.health.READ_HEART_RATE"

    val PERMISSIONS: Set<String> = setOf(
        PERM_READ_STEPS,
        PERM_READ_TOTAL_CAL,
        PERM_READ_DISTANCE,
        PERM_READ_HEARTRATE
    )

    fun getClient(context: Context): HealthConnectClient =
        HealthConnectClient.getOrCreate(context)

    fun isAvailable(context: Context): Boolean =
        HealthConnectClient.getSdkStatus(context, HEALTH_CONNECT_PACKAGE) ==
                HealthConnectClient.SDK_AVAILABLE


    fun requestPermissionsActivityContract(): ActivityResultContract<Set<String>, Set<String>> =
        PermissionController.createRequestPermissionResultContract()

    suspend fun hasAllPermissions(context: Context): Boolean {
        val client = getClient(context)
        val granted: Set<String> = client.permissionController.getGrantedPermissions()
        return granted.containsAll(PERMISSIONS)
    }


    suspend fun readStepsTotal(
        context: Context,
        start: Instant,
        end: Instant
    ): Long = withContext(Dispatchers.IO) {
        val client = getClient(context)
        val response = client.aggregate(
            AggregateRequest(
                metrics = setOf(StepsRecord.COUNT_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(start, end)
            )
        )
        response[StepsRecord.COUNT_TOTAL] ?: 0L
    }


    suspend fun readTotalCaloriesKcal(
        context: Context,
        start: Instant,
        end: Instant
    ): Double = withContext(Dispatchers.IO) {
        val client = getClient(context)
        val response = client.aggregate(
            AggregateRequest(
                metrics = setOf(TotalCaloriesBurnedRecord.ENERGY_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(start, end)
            )
        )
        response[TotalCaloriesBurnedRecord.ENERGY_TOTAL]?.inKilocalories ?: 0.0
    }

    suspend fun readDistanceMeters(
        context: Context,
        start: Instant,
        end: Instant
    ): Double = withContext(Dispatchers.IO) {
        val client = getClient(context)
        val response = client.aggregate(
            AggregateRequest(
                metrics = setOf(DistanceRecord.DISTANCE_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(start, end)
            )
        )
        // Distance object -> inMeters (fallback 0.0)
        response[DistanceRecord.DISTANCE_TOTAL]?.inMeters ?: 0.0
    }

    suspend fun readAverageHeartRate(
        context: Context,
        start: Instant,
        end: Instant
    ): Double = withContext(Dispatchers.IO) {
        val client = getClient(context)


        val request = ReadRecordsRequest(
            recordType = HeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(start, end)
        )
        val response = client.readRecords(request)

        val allBpm = response.records.flatMap { record ->
            record.samples.mapNotNull { sample ->
                sample.beatsPerMinute
            }
        }

        if (allBpm.isEmpty()) 0.0 else allBpm.average()
    }


    suspend fun getHealthDataForToday(context: Context): Pair<Long, Double> {
        val now = Instant.now()
        val startOfDay = now.truncatedTo(ChronoUnit.DAYS)
        val steps = readStepsTotal(context, startOfDay, now)
        val calories = readTotalCaloriesKcal(context, startOfDay, now)
        return Pair(steps, calories)
    }
}