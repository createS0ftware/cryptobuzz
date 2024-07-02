package uk.co.ht.cryptobuzz.presentation.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult.Error
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult.Loading
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult.Success
import uk.co.ht.cryptobuzz.domain.models.AssetInfoData
import uk.co.ht.cryptobuzz.domain.usecases.CoinUseCase
import uk.co.ht.cryptobuzz.domain.usecases.ExchangeUseCase
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val exchangeUseCase: ExchangeUseCase,
    private val coinUseCase: CoinUseCase
) : ViewModel() {

    private var topTenCoins: List<AssetInfoData> = mutableListOf()
    private val _primaryAssetStateFlow =
        MutableStateFlow<CoinCapRepositoryResult<AssetInfoData>>(Loading)
    private val _secondaryAssetStateFlow =
        MutableStateFlow<CoinCapRepositoryResult<AssetInfoData>>(Loading)

    val primaryAssetStateFlow: StateFlow<CoinCapRepositoryResult<AssetInfoData>> =
        _primaryAssetStateFlow.asStateFlow()
    val secondaryAssetStateFlow: StateFlow<CoinCapRepositoryResult<AssetInfoData>> =
        _secondaryAssetStateFlow.asStateFlow()
    val exchangeDataSelected = MutableLiveData<List<AssetInfoData>>()
    val coinDataSelected = MutableLiveData<List<AssetInfoData>>()

    fun getTopCoinAsset() {
        viewModelScope.launch {
            coinUseCase.getTopTenCoinAssets().collect { result ->
                when (result) {
                    is Error -> _primaryAssetStateFlow.value = Error(result.error, result.message)
                    is Loading -> _primaryAssetStateFlow.value = Loading
                    is Success -> {
                        topTenCoins = result.dataObject

                        _primaryAssetStateFlow.value = Success(topTenCoins[0])
                        _secondaryAssetStateFlow.value = Success(topTenCoins[1])
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
                        val exchangeList = result.dataObject

                        exchangeDataSelected.value = exchangeList
                    }
                }
            }
        }
    }

    private fun getTopTenCoins() {
        viewModelScope.launch {
            coinDataSelected.value = topTenCoins
        }
    }

    fun clickedExchanges() {
        getTopTenExchanges()
    }
    fun clickedCoins() {
        getTopTenCoins()
    }
}