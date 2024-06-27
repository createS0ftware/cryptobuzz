package uk.co.ht.base.domain.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.internal.connection.Exchange
import uk.co.ht.base.data.dto.CoinAsset

interface CoinCapRepository {
    suspend fun getTopTenExchanges(): Flow<CoinCapRepositoryResult<List<Exchange>>>

    suspend fun getTopTenCoins(): Flow<CoinCapRepositoryResult<List<CoinAsset>>>
}