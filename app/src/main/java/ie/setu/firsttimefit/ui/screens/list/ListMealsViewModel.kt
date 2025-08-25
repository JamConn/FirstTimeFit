package ie.setu.firsttimefit.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.data.repository.MealRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ListMealsViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _meals = MutableStateFlow<List<MealModel>>(emptyList())
    val uiMeals: StateFlow<List<MealModel>> = _meals.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllMeals().collect { list ->
                _meals.value = list
            }
        }
    }

    fun deleteMeal(meal: MealModel) {
        viewModelScope.launch {
            repository.deleteMeal(meal)
        }
    }
}