package uk.co.ht.cryptobuzz.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.cryptobuzz.domain.models.AssetInfoData
import java.text.DecimalFormat
import javax.inject.Inject

class ExchangeUseCase @Inject constructor(private val repository: CoinCapRepository) {
    suspend fun getTopTenExchangeAssets(): Flow<CoinCapRepositoryResult<List<AssetInfoData>>> =
        repository.getTopTenExchanges()
            .map { result ->
                when (result) {
                    is CoinCapRepositoryResult.Error -> {
                        CoinCapRepositoryResult.Error(result.error, result.message)
                    }

                    is CoinCapRepositoryResult.Loading -> {
                        CoinCapRepositoryResult.Loading
                    }

                    is CoinCapRepositoryResult.Success -> {
                        val topTenExchanges = result.dataObject
                            .map { exchange ->
                                AssetInfoData(
                                    exchange.name,
                                    exchange.rank.toInt(),
                                    getVolumeString(exchange.volumeUsd)
                                )
                            }
                        CoinCapRepositoryResult.Success(topTenExchanges)
                    }
                }
            }


    private fun getVolumeString(volume: String): String {
        val billion = 1_000_000_000
        val million = 1_000_000

        val number: Double = volume.toDouble()
        val decimalFormat = DecimalFormat("0.00")
        val sign = "$"

        return when {
            number >= billion -> {
                val value = number / billion
                "\$${decimalFormat.format(value)} Billion"
            }

            number >= million -> {
                val value = number / million
                "\$${decimalFormat.format(value)} Million"
            }

            else -> {
                "\$${decimalFormat.format(number)}"
            }
        }
    }


}


