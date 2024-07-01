package uk.co.ht.cryptobuzz.presentation.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uk.co.ht.cryptobuzz.presentation.components.CryptoBuzzTable
import uk.co.ht.cryptobuzz.presentation.theme.MaterialTheme

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashboardViewModel.exchangeDataSelected.observe(this) {
            setContent {
                CryptoBuzzTable(data = it)
            }
        }

        setContent {
            MaterialTheme {
                DashboardScreen()
            }
        }
    }
}
