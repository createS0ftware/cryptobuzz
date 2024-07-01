package uk.co.ht.cyberbuzz.presentation.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult.*
import uk.co.ht.cyberbuzz.R
import uk.co.ht.cyberbuzz.domain.models.CoinData
import uk.co.ht.cyberbuzz.domain.models.ExchangeData
import uk.co.ht.cyberbuzz.domain.usecases.CoinUseCase
import uk.co.ht.cyberbuzz.domain.usecases.ExchangeUseCase
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val exchangeUseCase: ExchangeUseCase,
    private val coinUseCase: CoinUseCase
) : ViewModel() {

    private val _primaryAssetStateFlow =
        MutableStateFlow<CoinCapRepositoryResult<CoinData>>(Loading)

    val primaryAssetStateFlow: StateFlow<CoinCapRepositoryResult<CoinData>> =
        _primaryAssetStateFlow.asStateFlow()

    private val _secondaryAssetStateFlow =
        MutableStateFlow<CoinCapRepositoryResult<CoinData>>(Loading)

    val secondaryAssetStateFlow: StateFlow<CoinCapRepositoryResult<CoinData>> =
        _secondaryAssetStateFlow.asStateFlow()

    val exchangeDataSelected = MutableLiveData<List<ExchangeData>>()

    fun getTopCoinAsset() {
        viewModelScope.launch {
            coinUseCase.getPrimaryCoinAsset().collect { result ->
                when (result) {
                    is Error -> _primaryAssetStateFlow.value = Error(result.error, result.message)
                    is Loading -> _primaryAssetStateFlow.value = Loading
                    is Success -> {
                        val percentChange: String =
                            getPercentChange(result.dataObject.changePercent24Hr)

                        val imageResource: Int = getImageResource(result.dataObject.name)

                        _primaryAssetStateFlow.value =
                            Success(CoinData(result.dataObject.name, percentChange, imageResource))
                    }
                }
            }
        }
    }

    private fun getTopTenExchanges() {
        viewModelScope.launch {
            exchangeUseCase.getTopTenExchanges().collect { result ->
                when (result) {
                    is Error -> {}
                    is Loading -> {}
                    is Success -> {
                        exchangeDataSelected.value = result.dataObject
                    }
                }
            }
        }
    }

    private fun getImageResource(name: String): Int {
        return R.drawable.placeholder;
    }

    private fun getPercentChange(changePercent24Hr: String): String {
        val changeValue: Double = changePercent24Hr.toDouble()
        val isNegative: Boolean = changeValue < 0.0
        val decimalFormat = DecimalFormat("0.00")
        val sign = if (isNegative) { "-" } else { "+" }
        return "$sign${decimalFormat.format(changeValue)} %"
    }

    fun clickedExchange() {
            getTopTenExchanges()
    }

}