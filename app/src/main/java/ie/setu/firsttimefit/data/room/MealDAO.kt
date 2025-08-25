package ie.setu.firsttimefit.data.room

import androidx.room.*
import ie.setu.firsttimefit.data.MealModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDAO {

    @Query("SELECT * FROM mealmodel")
    fun getAll(): Flow<List<MealModel>>

    @Query("SELECT * FROM mealmodel WHERE id=:id")
    fun get(id: Int): Flow<MealModel>

    @Insert
    suspend fun insert(meal: MealModel)

    @Query("UPDATE mealmodel SET description=:description WHERE id = :id")
    suspend fun update(id: Int, description: String)


    @Delete
    suspend fun delete(meal: MealModel)
}