package ie.setu.firsttimefit.data.model


import com.google.firebase.firestore.DocumentId
import java.util.Date

data class MealModel(
    @DocumentId val _id: String = "N/A",
    val mealType: String = "N/A",
    val calories: Int = 0,
    var description: String = "No notes",
    val dateAdded: Date = Date(),
    val dateModified: Date = Date(),
    var email: String = "jamie@fitfirsttime.com"
)


val fakeMeals = List(30) { i ->
    MealModel(
        _id = "meal_${i}",
        mealType = "Meal Type $i",
        calories = 100 + i * 10,
        description = "Notes $i",
        dateAdded = Date(),
        dateModified = Date()
    )
}