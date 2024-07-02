package uk.co.ht.cryptobuzz.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import uk.co.ht.base.data.dto.AssetsResponse
import uk.co.ht.base.data.dto.CoinAsset
import uk.co.ht.base.data.dto.ExchangeObject
import uk.co.ht.base.data.network.APIService
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult

@ExtendWith(MockitoExtension::class)
class CoinUseCaseTest {

    @Mock
    lateinit var coinService: APIService

    @Mock
    lateinit var assetResponse: AssetsResponse

    @InjectMocks
    lateinit var coinRepository: CoinCapRepository


    class MyFakeRepository : CoinCapRepository {
        private val mockCoins = listOf(
            CoinAsset(
                "bitcoin",
                "1",
                "BTC",
                "Bitcoin",
                "19719184.0000000000000000",
                "21000000.0000000000000000",
                "1242068991907.3672592984024368",
                "7690403923.6324000360854728",
                "62987.8493910989044627",
                "-0.8815444671869646",
                "63110.7413756479241857",
                "https://blockchain.info/"
            ), CoinAsset(
                "bitcoin",
                "2",
                "ETH",
                "Ethereum",
                "19719184.0000000000000000",
                "21000000.0000000000000000",
                "1242068991907.3672592984024368",
                "7690403923.6324000360854728",
                "62987.8493910989044627",
                "-0.8815444671869646",
                "63110.7413756479241857",
                "https://somechain.info/"
            )
        )

        override suspend fun getTopTenExchanges(): Flow<CoinCapRepositoryResult<List<ExchangeObject>>> =
            flow {
                emit(CoinCapRepositoryResult.Success(emptyList()))
            }

        override suspend fun getTopTenCoins(): Flow<CoinCapRepositoryResult<List<CoinAsset>>> =
            flow {
                emit(CoinCapRepositoryResult.Success(mockCoins))
            }

    }
    private val mockCoins = listOf(
        CoinAsset(
            "bitcoin",
            "1",
            "BTC",
            "Bitcoin",
            "19719184.0000000000000000",
            "21000000.0000000000000000",
            "1242068991907.3672592984024368",
            "7690403923.6324000360854728",
            "62987.8493910989044627",
            "-0.8815444671869646",
            "63110.7413756479241857",
            "https://blockchain.info/"
        ), CoinAsset(
            "bitcoin",
            "2",
            "ETH",
            "Ethereum",
            "19719184.0000000000000000",
            "21000000.0000000000000000",
            "1242068991907.3672592984024368",
            "7690403923.6324000360854728",
            "62987.8493910989044627",
            "-0.8815444671869646",
            "63110.7413756479241857",
            "https://somechain.info/"
        )
    )


    @Test
    fun `getTopTenCoinAssets returns Success result with appropriate result when repository returns Success result`() =
        runTest {
            val successResult = CoinCapRepositoryResult.Success(mockCoins)

            val coinUseCase = CoinUseCase(MyFakeRepository())
            val mockFlow: Flow<CoinCapRepositoryResult<List<CoinAsset>>> = flow {
                  emit(CoinCapRepositoryResult.Loading)
                  emit(CoinCapRepositoryResult.Success(mockCoins))
            }


            val result = coinUseCase.getTopTenCoinAssets().first()
            assertEquals(result, successResult)
        }

}