package ie.setu.firsttimefit.ui.screens.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.data.repository.MealRepository
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AddMealViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    fun insert(meal: MealModel) = viewModelScope.launch {
        repository.insertMeal(meal)
    }
}
