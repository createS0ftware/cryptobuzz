package uk.co.ht.cryptobuzz.presentation.dashboard

import CryptoBuzzTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uk.co.ht.cryptobuzz.presentation.components.CryptoBuzzTable

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashboardViewModel.exchangeDataSelected.observe(this) {
            setContent {
                CryptoBuzzTheme {
                    CryptoBuzzTable(data = it, showsCoins = false)
                }
            }
        }

        dashboardViewModel.coinDataSelected.observe(this) {
            setContent {
                CryptoBuzzTheme {
                    CryptoBuzzTable(data = it)
                }
            }
        }

        setContent {
            CryptoBuzzTheme {
                DashboardScreen()
            }
        }
    }
}
