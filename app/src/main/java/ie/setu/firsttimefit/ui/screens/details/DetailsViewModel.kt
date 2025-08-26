package ie.setu.firsttimefit.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.firsttimefit.data.model.MealModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import ie.setu.firsttimefit.firebase.services.AuthService
import ie.setu.firsttimefit.firebase.services.FirestoreService
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: FirestoreService,
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var meal = mutableStateOf(MealModel())
    val id: String = checkNotNull(savedStateHandle["id"])
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    init {
        viewModelScope.launch {
            try {
                isLoading.value = true
                meal.value =
                    repository.get(authService.email!!, id)!!
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
    }

    fun updateMeal(meal: MealModel) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.update(authService.email!!, meal)
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
    }
}