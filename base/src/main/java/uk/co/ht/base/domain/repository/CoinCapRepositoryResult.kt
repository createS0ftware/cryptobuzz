package uk.co.ht.base.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface CoinCapRepositoryResult<out T> {
    data class Success<T>(val dataObject: T) : CoinCapRepositoryResult<T>
    data class Error(val error: Throwable?, val message: String?) : CoinCapRepositoryResult<Nothing>
    object Loading : CoinCapRepositoryResult<Nothing>
}

fun <T> Flow<T>.asRepositoryResult(): Flow<CoinCapRepositoryResult<T>> {
    return this
        .map<T, CoinCapRepositoryResult<T>> {
            CoinCapRepositoryResult.Success(it)
        }
        .onStart { emit(CoinCapRepositoryResult.Loading) }
        .catch { emit(CoinCapRepositoryResult.Error(it, it.message)) }
}
