package uk.co.ht.cyberbuzz.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.co.ht.base.data.dto.CoinAsset
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult.*
import uk.co.ht.cyberbuzz.domain.usecases.CoinUseCase
import uk.co.ht.cyberbuzz.domain.usecases.ExchangeUseCase

class DashboardViewModel(
    private val exchangeUseCase: ExchangeUseCase,
    private val coinUseCase: CoinUseCase
) : ViewModel() {

    private val _primaryAssetStateFlow =
        MutableStateFlow<CoinCapRepositoryResult<CoinAsset>>(Loading)

    val primaryAssetStateFlow: StateFlow<CoinCapRepositoryResult<CoinAsset>> =
        _primaryAssetStateFlow.asStateFlow()

    private val _secondaryAssetStateFlow =
        MutableStateFlow<CoinCapRepositoryResult<CoinAsset>>(Loading)

    val secondaryAssetStateFlow: StateFlow<CoinCapRepositoryResult<CoinAsset>> =
        _secondaryAssetStateFlow.asStateFlow()


    fun getTopCoinAsset() {
        viewModelScope.launch {
            coinUseCase.getPrimaryCoinAsset().collect { result ->
                when (result) {
                    is Error -> TODO()
                    is Loading -> TODO()
                    is Success -> _primaryAssetStateFlow.value = Success(result.dataObject)
                }
            }
        }
    }

}