package ie.setu.firsttimefit.data

import java.util.Date
import kotlin.random.Random

data class MealModel(
    val id: Int = Random.nextInt(1, 100000),
    val mealType: String = "N/A",      // will correspond to radio button selection
    val calories: Int = 0,              // calories input via number picker
    val description: String = "No notes", // message/notes input by user
    val dateAdded: Date = Date()        // timestamp
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