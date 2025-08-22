package ie.setu.firsttimefit.data.room

import androidx.room.*
import ie.setu.firsttimefit.data.MealModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDAO {

    @Query("SELECT * FROM mealmodel")
    fun getAll(): Flow<List<MealModel>>

    @Insert
    suspend fun insert(meal: MealModel)

    @Update
    suspend fun update(meal: MealModel)

    @Delete
    suspend fun delete(meal: MealModel)
}