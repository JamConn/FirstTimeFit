package ie.setu.firsttimefit.data.repository

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ie.setu.firsttimefit.data.room.AppDatabase
import ie.setu.firsttimefit.data.room.MealDAO
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "firsttimefit_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMealDAO(appDatabase: AppDatabase): MealDAO = appDatabase.getMealDAO()

    @Provides
    fun provideMealRepository(mealDAO: MealDAO): MealRepository = MealRepository(mealDAO)
}