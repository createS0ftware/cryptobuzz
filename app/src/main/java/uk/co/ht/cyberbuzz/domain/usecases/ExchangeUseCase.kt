package uk.co.ht.cryptobuzz.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.cryptobuzz.domain.models.ExchangeData
import javax.inject.Inject

class ExchangeUseCase @Inject constructor(private val repository: CoinCapRepository) {
    suspend fun getTopTenExchanges(): Flow<CoinCapRepositoryResult<List<ExchangeData>>> =
        repository.getTopTenExchanges()
            .map { result ->
                when (result) {
                    is CoinCapRepositoryResult.Error -> { CoinCapRepositoryResult.Error (result.error, result.message) }
                    is CoinCapRepositoryResult.Loading -> { CoinCapRepositoryResult.Loading }
                    is CoinCapRepositoryResult.Success -> {
                        val topTenExchanges = result.dataObject
                            .map { exchange ->
                            ExchangeData( exchange.name, exchange.rank.toInt(), exchange.volumeUsd)
                        }
                        CoinCapRepositoryResult.Success(topTenExchanges)
                    }
                }
            }

}