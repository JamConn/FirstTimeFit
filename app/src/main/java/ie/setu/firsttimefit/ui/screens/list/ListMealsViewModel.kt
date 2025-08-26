package ie.setu.firsttimefit.ui.screens.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.firsttimefit.data.model.MealModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ie.setu.firsttimefit.firebase.services.AuthService
import ie.setu.firsttimefit.firebase.services.FirestoreService
import timber.log.Timber

@HiltViewModel
class ListMealsViewModel @Inject constructor(
    private val repository: FirestoreService,
    private val authService: AuthService
) : ViewModel() {

    private val _meals = MutableStateFlow<List<MealModel>>(emptyList())
    val uiMeals: StateFlow<List<MealModel>> = _meals.asStateFlow()

    var iserror = mutableStateOf(false)
    var isloading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

    init {
        getMeals()
    }

    fun getMeals() {
        viewModelScope.launch {
            try {
                isloading.value = true
                repository.getAll(authService.email!!).collect { items ->
                    _meals.value = items
                    iserror.value = false
                    isloading.value = false
                }
                Timber.i("Meals List = ${_meals.value}")
            } catch (e: Exception) {
                iserror.value = true
                isloading.value = false
                error.value = e
                Timber.i("ListMealsViewModel Error ${e.message}")
            }
        }
    }

    fun deleteMeal(meal: MealModel) =
        viewModelScope.launch {
            repository.delete(authService.email!!, meal._id)
        }
}