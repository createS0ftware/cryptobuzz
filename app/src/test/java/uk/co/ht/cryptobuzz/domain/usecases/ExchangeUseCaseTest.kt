package uk.co.ht.cryptobuzz.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.co.ht.base.data.dto.ExchangeObject
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.cryptobuzz.domain.models.AssetInfoData
import kotlin.test.assertEquals

class ExchangeUseCaseTest {

    @MockK
    private lateinit var repository: CoinCapRepository

    private lateinit var useCase: ExchangeUseCase


    private lateinit var expectedExchangeObjects: List<ExchangeObject>

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = ExchangeUseCase(repository)
        setUpDummyData()
    }

    /**
     * This test function verifies the behavior of [getTopTenExchangeAssets] function when [getTopTenExchanges]
     * returns a [CoinCapRepositoryResult.Success].
     *
     * @param repository.getTopTenExchanges A mock function that simulates the behavior of fetching top 10 exchanges.
     * It returns a flow of [CoinCapRepositoryResult.Success] containing a list of [ExchangeObject].
     *
     * @param useCase.getTopTenExchangeAssets The function under test that transforms the list of [ExchangeObject]
     * into a list of [AssetInfoData].
     *
     * @return [runTest] A coroutine that runs the test. It waits for the result of [getTopTenExchangeAssets]
     * and asserts that it matches the expected [CoinCapRepositoryResult.Success] with a list of [AssetInfoData].
     *
     * @throws AssertionError If the actual result does not match the expected result.
     */
    @Test
    fun `when getTopTenExchanges returns Success, getTopTenExchangeAssets should return Success with expected data`() =
        runTest {

            coEvery { repository.getTopTenExchanges() } returns flowOf(
                CoinCapRepositoryResult.Success(
                    expectedExchangeObjects
                )
            )

            val result = useCase.getTopTenExchangeAssets().first()

            assertEquals(
                CoinCapRepositoryResult.Success(
                    listOf(
                        AssetInfoData("Binance", 1, "$8.23 Billion"),
                        AssetInfoData("WhiteBIT", 2, "$3.06 Billion"),
                    )
                ), result
            )
        }

    /**
     * This test function verifies the behavior of [getTopTenExchangeAssets] function when [getTopTenExchanges]
     * returns a [CoinCapRepositoryResult.Loading].
     *
     * @param repository.getTopTenExchanges A mock function that simulates the behavior of fetching top 10 exchanges.
     * It returns a flow of [CoinCapRepositoryResult.Loading].
     *
     * @param useCase.getTopTenExchangeAssets The function under test that transforms the list of [ExchangeObject]
     * into a list of [AssetInfoData].
     *
     * @return [runTest] A coroutine that runs the test. It waits for the result of [getTopTenExchangeAssets]
     * and asserts that it matches the expected [CoinCapRepositoryResult.Loading].
     *
     * @throws AssertionError If the actual result does not match the expected result.
     */
    @Test
    fun `when getTopTenExchanges returns Loading, getTopTenExchangeAssets should return Loading`() =
        runTest {
            coEvery { repository.getTopTenExchanges() } returns flowOf(CoinCapRepositoryResult.Loading)

            val result = useCase.getTopTenExchangeAssets().first()

            assertEquals(CoinCapRepositoryResult.Loading, result)
        }

    /**
 * This test function verifies the behavior of [getTopTenExchangeAssets] function when [getTopTenExchanges]
 * returns a [CoinCapRepositoryResult.Error].
 *
 * @param repository.getTopTenExchanges A mock function that simulates the behavior of fetching top 10 exchanges.
 * It returns a flow of [CoinCapRepositoryResult.Error] containing an exception and an error message.
 *
 * @param useCase.getTopTenExchangeAssets The function under test that transforms the list of [ExchangeObject]
 * into a list of [AssetInfoData].
 *
 * @return [runTest] A coroutine that runs the test. It waits for the result of [getTopTenExchangeAssets]
 * and asserts that it matches the expected [CoinCapRepositoryResult.Error] with the same exception and error message.
 *
 * @throws AssertionError If the actual result does not match the expected result.
 */
@Test
fun `when getTopTenExchanges returns Error, getTopTenExchangeAssets should return Error with expected error message`() =
    runTest {
        val error = Exception("Network error")
        coEvery { repository.getTopTenExchanges() } returns flowOf(
            CoinCapRepositoryResult.Error(
                error,
                "Failed to fetch data"
            )
        )

        val result = useCase.getTopTenExchangeAssets().first()

        assertEquals(CoinCapRepositoryResult.Error(error, "Failed to fetch data"), result)
    }

    private fun setUpDummyData() {
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
    }

}