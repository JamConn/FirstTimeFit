package ie.setu.firsttimefit.firebase.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import ie.setu.firsttimefit.data.rules.Constants.MEAL_COLLECTION
import ie.setu.firsttimefit.data.rules.Constants.USER_EMAIL
import ie.setu.firsttimefit.firebase.services.AuthService
import ie.setu.firsttimefit.firebase.services.Meal
import ie.setu.firsttimefit.firebase.services.Meals
import ie.setu.firsttimefit.firebase.services.FirestoreService
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class FirestoreRepository
@Inject constructor(
    private val auth: AuthService,
    private val firestore: FirebaseFirestore
) : FirestoreService {

    override suspend fun getAll(email: String): Meals {
        return firestore.collection(MEAL_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .dataObjects()
    }

    override suspend fun get(email: String, mealId: String): Meal? {
        return firestore.collection(MEAL_COLLECTION)
            .document(mealId).get().await().toObject()
    }

    override suspend fun insert(email: String, meal: Meal) {
        val mealWithEmail = meal.copy(email = email)
        firestore.collection(MEAL_COLLECTION)
            .add(mealWithEmail)
            .await()
    }

    override suspend fun update(email: String, meal: Meal) {
        val mealWithModifiedDate = meal.copy(dateModified = Date())
        firestore.collection(MEAL_COLLECTION)
            .document(meal._id)
            .set(mealWithModifiedDate).await()
    }

    override suspend fun delete(email: String, mealId: String) {
        firestore.collection(MEAL_COLLECTION)
            .document(mealId)
            .delete().await()
    }
}