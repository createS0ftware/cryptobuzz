package uk.co.ht.cryptobuzz.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.co.ht.base.data.dto.CoinObject
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.cryptobuzz.domain.models.AssetInfoData

@ExperimentalCoroutinesApi
class CoinUseCaseTest {

    private lateinit var expectedCoinObjects: List<CoinObject>

    @MockK
    private lateinit var repository: CoinCapRepository

    private lateinit var coinUseCase: CoinUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        coinUseCase = CoinUseCase(repository)
        setUpDummyData()
    }

    /**
     * This test function verifies the behavior of [coinUseCase.getTopTenCoinAssets] when [repository.getTopTenCoins]
     * returns a [CoinCapRepositoryResult.Success] state.
     *
     * @see coinUseCase.getTopTenCoinAssets
     * @see repository.getTopTenCoins
     *
     * @return Nothing. This function is used for testing purposes only.
     *
     * Steps:
     * 1. Mocks [repository.getTopTenCoins] to return a flow of [CoinCapRepositoryResult.Success] with the provided
     *    [expectedCoinObjects].
     * 2. Calls [coinUseCase.getTopTenCoinAssets] and retrieves the first result.
     * 3. Verifies that [repository.getTopTenCoins] was called.
     * 4. Asserts that the first result is equal to [CoinCapRepositoryResult.Success] with the expected [expectedAssetInfoData].
     */
    @Test
    fun `when getTopTenCoins returns Success, getTopTenCoinAssets should return with list of AssetInfoData`() =
        runTest {

            val expectedAssetInfoData = listOf(
                AssetInfoData(assetName = "Bitcoin", assetRank = 1, assetValue = "+3.71 %"),
                AssetInfoData(assetName = "Ethereum", assetRank = 2, assetValue = "+6.77 %")
            )

            coEvery { repository.getTopTenCoins() } returns flowOf(
                CoinCapRepositoryResult.Success(
                    expectedCoinObjects
                )
            )

            val result = coinUseCase.getTopTenCoinAssets().first()

            coVerify { repository.getTopTenCoins() }
            assertEquals(CoinCapRepositoryResult.Success(expectedAssetInfoData), result)
        }


    /**
     * This test function verifies the behavior of [coinUseCase.getTopTenCoinAssets] when [repository.getTopTenCoins]
     * returns an [CoinCapRepositoryResult.Error] state.
     *
     * @param mockError The exception that represents the error.
     * @param expectedErrorMessage The expected error message.
     *
     * @return Nothing. This function is used for testing purposes only.
     *
     * Steps:
     * 1. Mocks [repository.getTopTenCoins] to return a flow of [CoinCapRepositoryResult.Error] with the provided
     *    [mockError] and [expectedErrorMessage].
     * 2. Calls [coinUseCase.getTopTenCoinAssets] and retrieves the first result.
     * 3. Verifies that [repository.getTopTenCoins] was called.
     * 4. Asserts that the first result is equal to [CoinCapRepositoryResult.Error] with the provided [mockError] and
     *    [expectedErrorMessage].
     */
    @Test
    fun `when getTopTenCoins returns Error, getTopTenCoinAssets should return with error and message`() =
        runTest {
            val mockError = Exception("Network error")
            val expectedErrorMessage = "Network error"

            coEvery { repository.getTopTenCoins() } returns flowOf(
                CoinCapRepositoryResult.Error(
                    mockError,
                    expectedErrorMessage
                )
            )

            val result = coinUseCase.getTopTenCoinAssets().first()

            coVerify { repository.getTopTenCoins() }
            assertEquals(CoinCapRepositoryResult.Error(mockError, expectedErrorMessage), result)
        }

    /**
     * This test function verifies the behavior of [coinUseCase.getTopTenCoinAssets] when [repository.getTopTenCoins]
     * returns a [CoinCapRepositoryResult.Loading] state.
     *
     * @see coinUseCase.getTopTenCoinAssets
     * @see repository.getTopTenCoins
     *
     * @return Nothing. This function is used for testing purposes only.
     *
     * Steps:
     * 1. Mocks [repository.getTopTenCoins] to return a flow of [CoinCapRepositoryResult.Loading] and then
     *    [CoinCapRepositoryResult.Success] with [expectedCoinObjects].
     * 2. Calls [coinUseCase.getTopTenCoinAssets] and retrieves the first result.
     * 3. Verifies that [repository.getTopTenCoins] was called.
     * 4. Asserts that the first result is equal to [CoinCapRepositoryResult.Loading].
     */
    @Test
    fun `when getTopTenCoins returns Loading, getTopTenCoinAssets should return with Loading state`() =
        runTest {
            coEvery { repository.getTopTenCoins() } returns flowOf(
                CoinCapRepositoryResult.Loading,
                CoinCapRepositoryResult.Success(expectedCoinObjects)
            )

            val result = coinUseCase.getTopTenCoinAssets().first()

            coVerify { repository.getTopTenCoins() }
            assertEquals(CoinCapRepositoryResult.Loading, result)
        }


    /**
     * This function is used to retrieve the secondary coin asset from the top ten coins list.
     * It checks if the list has at least two items. If not, it returns an error with the message "Not enough data".
     * If the list has at least two items, it maps the second item to an [AssetInfoData] object and returns it as a success.
     *
     * @return A [CoinCapRepositoryResult] object representing the result of the operation.
     * - If the operation is successful, it returns [CoinCapRepositoryResult.Success] with the secondary coin asset data.
     * - If the operation encounters an error, it returns [CoinCapRepositoryResult.Error] with the error message.
     */
    @Test
    fun `when getTopTenCoins returns only one item, getSecondaryCoinAsset returns error`() =
        runTest {

            val shortList = expectedCoinObjects.subList(0, 1)
            coEvery { repository.getTopTenCoins() } returns flowOf(
                CoinCapRepositoryResult.Success(shortList)
            )

            // When
            val result = coinUseCase.getSecondaryCoinAsset().first()

            // Then
            assertEquals(
                "Not enough data",
                (result as CoinCapRepositoryResult.Error).message
            )
        }
/*
    @Test
    fun `when getSecondaryCoinAsset returns success with secondary coin asset`() = runTest {

        coEvery { repository.getTopTenCoins() } returns flowOf(
            CoinCapRepositoryResult.Success(expectedCoinObjects)
        )

        // When
        val result = coinUseCase.getSecondaryCoinAsset().first()

        // Then
        assertEquals(
            CoinCapRepositoryResult.Success(
                AssetInfoData(assetName = "Ethereum", assetRank = 2, assetValue = "+6.77 %")
            ),
            result
        )
    }*/


    private fun setUpDummyData() {
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

