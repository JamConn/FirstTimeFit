package ie.setu.firsttimefit.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import kotlin.random.Random

@Entity
data class MealModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mealType: String = "N/A",
    val calories: Int = 0,
    val description: String = "No notes",
    val dateAdded: Date = Date()
)

// Fake meals for testing and preview
val fakeMeals = List(30) { i ->
    MealModel(
        id = 12345 + i,
        mealType = "Meal Type $i",
        calories = 100 + i * 10,
        description = "Notes $i",
        dateAdded = Date()
    )
}