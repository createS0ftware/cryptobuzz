package uk.co.ht.cryptobuzz.domain.usecases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import uk.co.ht.base.data.dto.CoinAsset
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class CoinUseCase @Inject constructor(private val repository: CoinCapRepository) {

    private val SECONDARY_COIN_INDEX = 1;

    suspend fun getTopTenCoinAssets():
            Flow<CoinCapRepositoryResult<List<CoinAsset>>> = repository.getTopTenCoins()

    suspend fun getPrimaryCoinAsset(): Flow<CoinCapRepositoryResult<CoinAsset>> =

        repository.getTopTenCoins()
            .map { result ->
                when (result) {
                    is CoinCapRepositoryResult.Success -> {
                        val firstCoinAsset = result.dataObject.firstOrNull()
                        if (firstCoinAsset != null) {
                            CoinCapRepositoryResult.Success(firstCoinAsset)
                        } else {
                            CoinCapRepositoryResult.Error(
                                Exception("No CoinAsset found"),
                                "No CoinAsset found"
                            )
                        }
                    }

                    is CoinCapRepositoryResult.Loading -> {
                        CoinCapRepositoryResult.Loading
                    }

                    is CoinCapRepositoryResult.Error -> {
                        CoinCapRepositoryResult.Error(result.error, result.message)
                    }
                }
            }


    suspend fun getSecondaryCoinAsset(): Flow<CoinCapRepositoryResult<CoinAsset>> =
        flow {
            repository.getTopTenCoins()
                .collect { coinsResult ->
                    when (coinsResult) {
                        is CoinCapRepositoryResult.Error -> TODO()
                        is CoinCapRepositoryResult.Loading -> {}
                        is CoinCapRepositoryResult.Success -> {
                            coinsResult.dataObject[SECONDARY_COIN_INDEX]
                        }
                    }
                }

        }
}