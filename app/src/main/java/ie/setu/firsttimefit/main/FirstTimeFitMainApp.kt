package ie.setu.firsttimefit.main

import android.app.Application
import timber.log.Timber

class FirstTimeFitMainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting FirstTimeFit")

    }
}
