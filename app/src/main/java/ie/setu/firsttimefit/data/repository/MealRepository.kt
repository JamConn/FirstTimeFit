package ie.setu.firsttimefit.data.repository

import ie.setu.firsttimefit.data.MealModel
import ie.setu.firsttimefit.data.room.MealDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepository @Inject constructor(private val mealDAO: MealDAO) {

    fun getAllMeals(): Flow<List<MealModel>> = mealDAO.getAll()

    fun getMeal(id: Int): Flow<MealModel> = mealDAO.get(id)

    suspend fun insertMeal(meal: MealModel) = mealDAO.insert(meal)

    suspend fun updateMeal(meal: MealModel) {
        mealDAO.update(meal.id, meal.description)
    }
    suspend fun deleteMeal(meal: MealModel) = mealDAO.delete(meal)
}