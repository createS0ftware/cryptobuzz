package uk.co.ht.base.domain.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.internal.connection.Exchange
import uk.co.ht.base.data.dto.CoinAsset
import uk.co.ht.base.data.dto.ExchangeObject

interface CoinCapRepository {
    suspend fun getTopTenExchanges(): Flow<CoinCapRepositoryResult<List<ExchangeObject>>>

    suspend fun getTopTenCoins(): Flow<CoinCapRepositoryResult<List<CoinAsset>>>
}