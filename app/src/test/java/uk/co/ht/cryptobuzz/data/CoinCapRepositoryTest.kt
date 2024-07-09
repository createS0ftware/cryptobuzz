package uk.co.ht.cryptobuzz.data

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.co.ht.base.data.dto.AssetsResponse
import uk.co.ht.base.data.dto.CoinObject
import uk.co.ht.base.data.dto.ExchangeObject
import uk.co.ht.base.data.network.APIService
import uk.co.ht.base.data.network.CoinCapRepositoryImpl
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import kotlin.test.DefaultAsserter.fail
import kotlin.test.assertNotNull

class CoinCapRepositoryTest {


    private lateinit var expectedExchangeObjects: List<ExchangeObject>
    private lateinit var expectedCoinObjects: List<CoinObject>

    @BeforeEach
    fun setUp() {
        setupDummyData()
    }

    @DisplayName("CoinCapRepositoryResult Flow Tests")
    @Nested
    inner class RepositoryResultFlowTests {

        @MockK
        private lateinit var coinCapRepository : CoinCapRepository

        @BeforeEach
        fun setUp() {
            MockKAnnotations.init(this)
        }


        @Test
        fun `getTopTenExchanges returns a flow of CoinCapRepositoryResult with a list of ExchangeObjects`() =
            runTest {
                coEvery { coinCapRepository.getTopTenExchanges() } returns flowOf(
                    CoinCapRepositoryResult.Success(expectedExchangeObjects)
                )

                val result = coinCapRepository.getTopTenExchanges().first()

                coVerify { coinCapRepository.getTopTenExchanges() }
                assert(result is CoinCapRepositoryResult.Success)
                assertEquals(
                    expectedExchangeObjects,
                    (result as CoinCapRepositoryResult.Success).dataObject
                )
            }

        @Test
        fun `getTopTenCoins returns a flow of CoinCapRepositoryResult with a list of CoinAssets`() =
            runTest {
                coEvery { coinCapRepository.getTopTenCoins() } returns flowOf(
                    CoinCapRepositoryResult.Success(
                        expectedCoinObjects
                    )
                )

                val result = coinCapRepository.getTopTenCoins().first()

                coVerify { coinCapRepository.getTopTenCoins() }
                assert(result is CoinCapRepositoryResult.Success)
                assertEquals(
                    expectedCoinObjects,
                    (result as CoinCapRepositoryResult.Success).dataObject
                )
            }

        @Test
        fun `getTopTenExchanges returns a flow of CoinCapRepositoryResult with an error`() =
            runTest {
                val expectedError = Exception("Error fetching top ten exchanges")
                coEvery { coinCapRepository.getTopTenExchanges() } returns flowOf(
                    CoinCapRepositoryResult.Error(
                        expectedError,
                        expectedError.message
                    )
                )

                val result = coinCapRepository.getTopTenExchanges().first()

                coVerify { coinCapRepository.getTopTenExchanges() }
                assert(result is CoinCapRepositoryResult.Error)
                assertEquals(expectedError, (result as CoinCapRepositoryResult.Error).error)
            }

        @Test
        fun `getTopTenCoins returns a flow of CoinCapRepositoryResult with an error`() = runTest {
            val expectedError = Exception("Error fetching top ten coins")
            coEvery { coinCapRepository.getTopTenCoins() } returns flowOf(
                CoinCapRepositoryResult.Error(
                    expectedError,
                    expectedError.message
                )
            )

            val result = coinCapRepository.getTopTenCoins().first()

            coVerify { coinCapRepository.getTopTenCoins() }
            assert(result is CoinCapRepositoryResult.Error)
            assertEquals(expectedError, (result as CoinCapRepositoryResult.Error).error)
        }
    }

    @DisplayName("CoinCapRepository API Tests")
    @Nested
    inner class RepositoryApiTests {

        @RelaxedMockK
        private lateinit var apiService: APIService

        @RelaxedMockK
        private lateinit var mockRepository: CoinCapRepository

        @BeforeEach
        fun setUp() {
            MockKAnnotations.init(this)
            setupRepository()
        }

        /**
         * This function tests the behavior of the [mockRepository] when calling the [getTopTenExchanges] function.
         * It uses [coEvery] to mock the [apiService.getExchanges] function to return a successful result with the [expectedExchangeObjects].
         * The function then collects the results from the [resultFlow] returned by [mockRepository.getTopTenExchanges].
         *
         * The function verifies the following scenarios:
         * 1. When the API call is successful, it checks if the result is of type [CoinCapRepositoryResult.Success] and
         *    compares the [expectedExchangeObjects] with the data object in the result.
         * 2. When the API call fails, it checks if the result is of type [CoinCapRepositoryResult.Error] and
         *    logs the error message.
         * 3. During the API call, it checks if the result is of type [CoinCapRepositoryResult.Loading] before the successful or error result.
         *
         * @see [mockRepository]
         * @see [getTopTenExchanges]
         * @see [apiService.getExchanges]
         * @see [CoinCapRepositoryResult]
         * @see [expectedExchangeObjects]
         */
        @Test
        fun `repository calls API to get exchanges and returns expected data successfully`() =
            runTest {
                coEvery { apiService.getExchanges() } returns Result.success(mockk {
                    every { data } returns expectedExchangeObjects
                })

                val resultFlow: Flow<CoinCapRepositoryResult<List<ExchangeObject>>> =
                    mockRepository.getTopTenExchanges()

                lateinit var lastResult: CoinCapRepositoryResult.Loading

                resultFlow.collect { result ->
                    when (result) {
                        is CoinCapRepositoryResult.Success -> {
                            // Verify the success case
                            val exchanges = result.dataObject
                            assertNotNull(lastResult)
                            assertEquals(expectedExchangeObjects, exchanges)
                        }

                        is CoinCapRepositoryResult.Error -> {
                            // Verify the error case
                            fail("Expected success, but got error: ${result.error}")
                        }

                        CoinCapRepositoryResult.Loading -> {
                            //Save the result for later to verify the sequence of events
                            lastResult = result as CoinCapRepositoryResult.Loading
                            // Verify the loading case
                            assertEquals(result, CoinCapRepositoryResult.Loading)
                        }
                    }
                }
            }

        /**
         * This test function verifies the behavior of the [mockRepository] when calling the [getTopTenCoins] function.
         * It uses [coEvery] to mock the [apiService.getCoins] function to return a successful result with the [expectedCoinObjects].
         * The function then collects the results from the [resultFlow] returned by [mockRepository.getTopTenCoins].
         *
         * The function verifies the following scenarios:
         * 1. When the API call is successful, it checks if the result is of type [CoinCapRepositoryResult.Success] and
         *    compares the [expectedCoinObjects] with the data object in the result.
         * 2. When the API call fails, it checks if the result is of type [CoinCapRepositoryResult.Error] and
         *    logs the error message.
         * 3. During the API call, it checks if the result is of type [CoinCapRepositoryResult.Loading] before the successful or error result.
         *
         * @see [mockRepository]
         * @see [getTopTenCoins]
         * @see [apiService.getCoins]
         * @see [CoinCapRepositoryResult]
         * @see [expectedCoinObjects]
         */
        @Test
        fun `repository calls API to get coins and returns expected data successfully`() =
            runTest {
                coEvery { apiService.getCoins() } returns Result.success(mockk<AssetsResponse> {
                    every { data } returns expectedCoinObjects
                })

                val resultFlow: Flow<CoinCapRepositoryResult<List<CoinObject>>> =
                    mockRepository.getTopTenCoins()

                lateinit var lastResult: CoinCapRepositoryResult.Loading

                resultFlow.collect { result ->
                    when (result) {
                        is CoinCapRepositoryResult.Success -> {
                            // Verify the success case
                            val coins = result.dataObject
                            assertNotNull(lastResult)
                            assertEquals(expectedCoinObjects, coins)
                        }

                        is CoinCapRepositoryResult.Error -> {
                            // Verify the error case
                            fail("Expected success, but got error: ${result.error}")
                        }

                        CoinCapRepositoryResult.Loading -> {
                            //Save the result for later to verify the sequence of events
                            lastResult = result as CoinCapRepositoryResult.Loading
                            // Verify the loading case
                            assertEquals(result, CoinCapRepositoryResult.Loading)
                        }
                    }
                }
            }

        private fun setupRepository() {
            mockRepository = CoinCapRepositoryImpl(apiService)
        }
    }

    private fun setupDummyData() {
        expectedExchangeObjects = listOf(
            ExchangeObject(
                exchangeId = "binance",
                name = "Binance",
                rank = "1",
                percentTotalVolume = "31.579022203771569232000000000000000000",
                volumeUsd = "8227686811.4058265161698505",
                tradingPairs = "756",
                socket = true,
                exchangeUrl = "https://www.binance.com/",
                updated = 1720488682672
            ),
            ExchangeObject(
                exchangeId = "whitebit",
                name = "WhiteBIT",
                rank = "2",
                percentTotalVolume = "11.746933690994200753000000000000000000",
                volumeUsd = "3060578974.8711271590969528",
                tradingPairs = "87",
                socket = false,
                exchangeUrl = "https://whitebit.com",
                updated = 1720488697161
            )
        )

        expectedCoinObjects = listOf(
            CoinObject(
                id = "bitcoin",
                rank = "1",
                symbol = "BTC",
                name = "Bitcoin",
                supply = "19719700.0000000000000000",
                maxSupply = "21000000.0000000000000000",
                marketCapUsd = "1117426544216.3749946053533500",
                volumeUsd24Hr = "17155752753.8563318906467711",
                priceUsd = "56665.4941107813503555",
                changePercent24Hr = "3.7130935833099257",
                vwap24Hr = "56238.8988592895148501",
                explorer = "https://blockchain.info/"
            ),
            CoinObject(
                id = "ethereum",
                rank = "2",
                symbol = "ETH",
                name = "Ethereum",
                supply = "120205195.7912226800000000",
                maxSupply = null,
                marketCapUsd = "364322855061.0157458522629949",
                volumeUsd24Hr = "12935777696.4879498114334795",
                priceUsd = "3030.8411600925024955",
                changePercent24Hr = "6.7677175334618172",
                vwap24Hr = "2975.7691332455942308",
                explorer = "https://etherscan.io/"
            )
        )
    }

}