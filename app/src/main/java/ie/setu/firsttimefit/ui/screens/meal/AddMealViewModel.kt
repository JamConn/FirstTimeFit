package ie.setu.firsttimefit.ui.screens.meal

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.firsttimefit.data.model.MealModel
import ie.setu.firsttimefit.firebase.services.AuthService
import ie.setu.firsttimefit.firebase.services.FirestoreService
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber


@HiltViewModel
class AddMealViewModel @Inject
constructor(private val repository: FirestoreService,
            private val authService: AuthService
)
    : ViewModel() {
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    fun insert(meal: MealModel) =
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.insert(authService.email!!,meal)
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
            Timber.i("DVM Insert Message = : ${error.value.message} and isError ${isErr.value}")
        }
}

