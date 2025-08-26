package ie.setu.firsttimefit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ie.setu.firsttimefit.ui.screens.home.HomeScreen
import ie.setu.firsttimefit.ui.theme.FirstTimeFitTheme
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstTimeFitMainActivity : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstTimeFitTheme { HomeScreen() }
        }
    }
}