package ie.setu.firsttimefit

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class FirstTimeFitMainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.Forest.plant(Timber.DebugTree())
        Timber.Forest.i("Starting FirstTimeFit")
        Firebase.initialize(context = this)

    }
}