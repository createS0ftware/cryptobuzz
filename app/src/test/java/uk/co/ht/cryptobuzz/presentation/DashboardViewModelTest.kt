package uk.co.ht.cryptobuzz.presentation

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult.*
import uk.co.ht.cryptobuzz.domain.models.AssetInfoData
import uk.co.ht.cryptobuzz.domain.usecases.CoinUseCase
import uk.co.ht.cryptobuzz.domain.usecases.ExchangeUseCase
import uk.co.ht.cryptobuzz.presentation.dashboard.DashboardViewModel


@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel

    @RelaxedMockK
    private lateinit var coinUseCase: CoinUseCase

    @RelaxedMockK
    private lateinit var exchangeUseCase: ExchangeUseCase

    private val testDispatcher = StandardTestDispatcher()

    private val coin1 = AssetInfoData("coin1", 1, "+1.0 %")
    private val coin2 = AssetInfoData("coin2", 2, "-10.0 %")

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(exchangeUseCase, coinUseCase)
    }

    /**
     * This test function verifies the behavior of [DashboardViewModel.getTopCoinAsset] when it receives a
     * [Loading] response from the [coinUseCase.getTopTenCoinAssets] function.
     *
     * @param runTest A coroutine test function provided by the JUnit 5 framework.
     * @param coinUseCase A mock object implementing the [CoinUseCase] interface.
     * @param exchangeUseCase A mock object implementing the [ExchangeUseCase] interface.
     * @param testDispatcher A custom test dispatcher for running coroutines.
     * @param coin1 An instance of [AssetInfoData] representing a cryptocurrency asset.
     * @param coin2 An instance of [AssetInfoData] representing a cryptocurrency asset.
     * @param viewModel An instance of [DashboardViewModel] under test.
     *
     * The function sets up a mock response from the [coinUseCase] using [coEvery] to return a flow that emits
     * [Loading] initially. When [viewModel.getTopCoinAsset] is called, it should update the
     * [primaryAssetStateFlow] and [secondaryAssetStateFlow] with the received [Loading] state.
     *
     * The test uses [runTest] to run the coroutine test and asserts that the [primaryAssetStateFlow] and
     * [secondaryAssetStateFlow] contain [Loading] after the flow emits the value. Additionally, it verifies
     * that the [coinUseCase.getTopTenCoinAssets] function is called exactly once.
     */
    @Test
    fun `when receiving Loading state from getTopTenCoinAssets, check that it is correctly propagated from use cases to primaryAssetStateFlow`() =
        runTest {
            // Given
            coEvery { coinUseCase.getTopTenCoinAssets() } returns flow {
                emit(Loading)
            }

            // When
            viewModel.getTopCoinAsset()

            // Then
            assertEquals(Loading, viewModel.primaryAssetStateFlow.value)
            assertEquals(Loading, viewModel.secondaryAssetStateFlow.value)
            advanceTimeBy(1) //
            coVerify(exactly = 1) { coinUseCase.getTopTenCoinAssets() }
        }

    /**
     * This test function verifies the behavior of [DashboardViewModel.getTopCoinAsset] when it receives a
     * [Success] response with 2 coins from the [coinUseCase.getTopTenCoinAssets] function.
     *
     * The function sets up a mock response from the [coinUseCase] using [coEvery] to return a flow that emits
     * [Loading] initially and then [Success] with a list of 2 coins ([coin1] and [coin2]).
     *
     * When [viewModel.getTopCoinAsset] is called, it should update the [primaryAssetStateFlow] and
     * [secondaryAssetStateFlow] with the received coins.
     *
     * The test uses [runTest] to run the coroutine test and [advanceTimeBy] to advance the clock to allow the
     * flow to emit the next value.
     *
     * The expected outcome is that the [primaryAssetStateFlow] and [secondaryAssetStateFlow] should contain
     * [Success] with the respective coins after the flow emits the next value.
     */
    @Test
    fun `getTopCoinAsset handles Success response with 2 coins from getTopTenCoinAssets, updates primary and secondary asset state flows`() =
        runTest {
            // Given
            coEvery { coinUseCase.getTopTenCoinAssets() } returns flow {
               // emit(Loading)
                emit(Success(listOf(coin1, coin2)))
            }

            // When
            viewModel.getTopCoinAsset()

            // Then
            assertEquals(Loading, viewModel.primaryAssetStateFlow.value)
            assertEquals(Loading, viewModel.secondaryAssetStateFlow.value)

            advanceTimeBy(1) // Advance the clock to allow the flow to emit the next value

            assertEquals(Success(coin1), viewModel.primaryAssetStateFlow.value)
            assertEquals(Success(coin2), viewModel.secondaryAssetStateFlow.value)
        }

}