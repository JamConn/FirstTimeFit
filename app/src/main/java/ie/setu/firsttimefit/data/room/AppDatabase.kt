package ie.setu.firsttimefit.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.setu.firsttimefit.data.MealModel

@Database(entities = [MealModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMealDAO(): MealDAO
}