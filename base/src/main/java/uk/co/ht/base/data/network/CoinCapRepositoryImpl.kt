package uk.co.ht.base.data.network

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import okhttp3.internal.connection.Exchange
import uk.co.ht.base.data.dto.AssetsResponse
import uk.co.ht.base.data.dto.CoinAsset
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import javax.inject.Inject


class CoinCapRepositoryImpl @Inject constructor(private val apiService: APIService) :
    CoinCapRepository {

    private val TOP_N = 10;

    override suspend fun getTopTenExchanges(): Flow<CoinCapRepositoryResult<List<Exchange>>> =
        flow {
            apiService.getExchanges()
                .onSuccess {
                    emit(CoinCapRepositoryResult.Success(it.data.take(TOP_N)))
                }
                .onFailure { emit(CoinCapRepositoryResult.Error(it, it.message)) }
        }.onStart { emit(CoinCapRepositoryResult.Loading) }

    override suspend fun getTopTenCoins(): Flow<CoinCapRepositoryResult<List<CoinAsset>>> =
        flow<CoinCapRepositoryResult<List<CoinAsset>>> {
            apiService.getAssets()
                .onSuccess { emit(CoinCapRepositoryResult.Success(it.data.take(TOP_N))) }
                .onFailure { emit(CoinCapRepositoryResult.Error(it, it.message)) }
        }.onStart { emit(CoinCapRepositoryResult.Loading) }

}