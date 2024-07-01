package uk.co.ht.cyberbuzz.presentation.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uk.co.ht.cyberbuzz.presentation.components.CyberBuzzTable
import uk.co.ht.cyberbuzz.presentation.theme.CyberBuzzTheme

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashboardViewModel.exchangeDataSelected.observe(this) {
            setContent {
                CyberBuzzTable(data = it)
            }
        }

        setContent {
            CyberBuzzTheme {
                DashboardScreen()
            }
        }
    }
}
