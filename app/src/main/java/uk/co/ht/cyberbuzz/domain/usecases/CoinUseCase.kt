package uk.co.ht.cryptobuzz.domain.usecases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.cryptobuzz.domain.models.AssetInfoData
import java.text.DecimalFormat
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class CoinUseCase @Inject constructor(private val repository: CoinCapRepository) {

    private val SECONDARY_COIN_INDEX = 1;

    suspend fun getTopTenCoinAssets(): Flow<CoinCapRepositoryResult<List<AssetInfoData>>> =
        repository.getTopTenCoins()
            .map { result ->
                when (result) {
                    is CoinCapRepositoryResult.Error -> CoinCapRepositoryResult.Error(
                        result.error,
                        result.message
                    )

                    is CoinCapRepositoryResult.Loading -> CoinCapRepositoryResult.Loading
                    is CoinCapRepositoryResult.Success -> {
                        val topTenCoins = result.dataObject
                            .map { coin ->
                                val percentChange: String =
                                    getPercentChange(coin.changePercent24Hr)

                                AssetInfoData(
                                    coin.name,
                                    coin.rank.toInt(),
                                    percentChange,
                                )
                            }
                        CoinCapRepositoryResult.Success(topTenCoins)
                    }
                }
            }

    suspend fun getSecondaryCoinAsset(): Flow<CoinCapRepositoryResult<AssetInfoData>> =
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

    private fun getPercentChange(changePercent24Hr: String): String {
        val changeValue: Double = changePercent24Hr.toDouble()
        val isNegative: Boolean = changeValue < 0.0
        val decimalFormat = DecimalFormat("0.00")
        val sign = if (isNegative) {
            "-"
        } else {
            "+"
        }

        return "$sign${decimalFormat.format(changeValue)} %"
    }
}