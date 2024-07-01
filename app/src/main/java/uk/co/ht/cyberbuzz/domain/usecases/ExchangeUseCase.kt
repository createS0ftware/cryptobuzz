package uk.co.ht.cyberbuzz.domain.usecases

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import okhttp3.internal.connection.Exchange
import uk.co.ht.base.data.dto.CoinAsset
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.base.domain.repository.asRepositoryResult
import javax.inject.Inject

class ExchangeUseCase @Inject constructor(private val repository: CoinCapRepository) {
    suspend fun getTopTenExchanges(): Flow<CoinCapRepositoryResult<List<Exchange>>> =
        repository.getTopTenExchanges()
}