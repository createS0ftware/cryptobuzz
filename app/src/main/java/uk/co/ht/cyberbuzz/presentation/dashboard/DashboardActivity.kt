package uk.co.ht.cyberbuzz.presentation.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import uk.co.ht.cyberbuzz.presentation.theme.CyberBuzzTheme

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            CyberBuzzTheme {
                DashboardScreen()
            }
        }
    }
}
