package uk.co.ht.base.domain.repository

import kotlinx.coroutines.flow.Flow
import uk.co.ht.base.data.dto.CoinObject
import uk.co.ht.base.data.dto.ExchangeObject

interface CoinCapRepository {
    suspend fun getTopTenExchanges(): Flow<CoinCapRepositoryResult<List<ExchangeObject>>>

    suspend fun getTopTenCoins(): Flow<CoinCapRepositoryResult<List<CoinObject>>>

}