package ie.setu.firsttimefit.firebase.services

import ie.setu.firsttimefit.data.model.MealModel
import kotlinx.coroutines.flow.Flow

typealias Meal = MealModel
typealias Meals = Flow<List<Meal>>

interface FirestoreService {

    suspend fun getAll(email: String): Meals
    suspend fun get(email: String, mealId: String): Meal?
    suspend fun insert(email: String, meal: Meal)
    suspend fun update(email: String, meal: Meal)
    suspend fun delete(email: String, mealId: String)
}