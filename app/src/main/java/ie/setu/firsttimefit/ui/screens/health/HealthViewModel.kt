package ie.setu.firsttimefit.ui.screens.health

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ie.setu.firsttimefit.data.model.HealthData
import ie.setu.firsttimefit.ui.components.general.HealthConnectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _healthData = MutableStateFlow(HealthData())
    val healthData: StateFlow<HealthData> = _healthData

    private val _permissionsGranted = MutableStateFlow(false)
    val permissionsGranted: StateFlow<Boolean> = _permissionsGranted

    fun checkPermissions() {
        viewModelScope.launch {
            _permissionsGranted.value = HealthConnectManager.hasAllPermissions(context)
        }
    }

    fun loadHealthData() {
        viewModelScope.launch {
            try {
                val (steps, calories) = HealthConnectManager.getHealthDataForToday(context)
                _healthData.value = HealthData(steps = steps, calories = calories)
            } catch (e: Exception) {
                _healthData.value = HealthData()
            }
        }
    }
}