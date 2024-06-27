package uk.co.ht.cyberbuzz.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import uk.co.ht.base.data.dto.CoinAsset
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.base.domain.repository.asRepositoryResult
import java.util.Locale.filter

class CoinUseCase(private val repository: CoinCapRepository) {

    private val SECONDARY_COIN_INDEX = 1;

    suspend fun getTopTenCoinAssets():
            Flow<CoinCapRepositoryResult<List<CoinAsset>>> = repository.getTopTenCoins()

    suspend fun getPrimaryCoinAsset(): Flow<CoinCapRepositoryResult<CoinAsset>> =
        flow {
            repository.getTopTenCoins()
                .first()

        }

    suspend fun getSecondaryCoinAsset(): Flow<CoinCapRepositoryResult<CoinAsset>> =
        flow {
            repository.getTopTenCoins()
                .collect{ coinsResult ->
                    when (coinsResult) {
                        is CoinCapRepositoryResult.Error -> TODO()
                        is CoinCapRepositoryResult.Loading -> {}
                        is CoinCapRepositoryResult.Success -> {
                            coinsResult.dataObject[SECONDARY_COIN_INDEX]
                        }
                    }                    }

        }
}