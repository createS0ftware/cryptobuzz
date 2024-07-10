package uk.co.ht.cryptobuzz.presentation.dashboard

import CryptoBuzzTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dagger.hilt.android.AndroidEntryPoint
import uk.co.ht.cryptobuzz.presentation.components.CryptoBuzzTable

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val isBackPressed = mutableStateOf(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backPressedDispatcher = this.onBackPressedDispatcher


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
            val backPressed = remember { isBackPressed }
            if (!backPressed.value) {
                CryptoBuzzTheme {
                    DashboardScreen()
                }
            }
        }

        backPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!isBackPressed.value) {
                        isBackPressed.value = true
                        setContent {
                            CryptoBuzzTheme {
                                DashboardScreen()
                            }
                        }
                    } else {
                        finish()
                    }
                }
            })
    }
}
