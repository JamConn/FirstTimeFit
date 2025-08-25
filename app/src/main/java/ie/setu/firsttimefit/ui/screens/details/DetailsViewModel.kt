package ie.setu.firsttimefit.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.data.repository.MealRepository
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var meal = mutableStateOf(MealModel())
    val id: Int = checkNotNull(savedStateHandle["id"])

    init {
        viewModelScope.launch {
            repository.getMeal(id).collect { objMeal ->
                meal.value = objMeal
            }
        }
    }

    fun updateMeal(meal: MealModel) {
        viewModelScope.launch { repository.updateMeal(meal) }
    }
}