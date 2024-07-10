package uk.co.ht.cryptobuzz.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.cryptobuzz.domain.models.AssetInfoData
import uk.co.ht.cryptobuzz.domain.usecases.CoinUseCase
import uk.co.ht.cryptobuzz.domain.usecases.ExchangeUseCase
import uk.co.ht.cryptobuzz.presentation.dashboard.DashboardViewModel
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
class DashboardViewModelLiveDataTest {

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    private lateinit var exchangeUseCase: ExchangeUseCase

    @RelaxedMockK
    private lateinit var coinUseCase: CoinUseCase

    private lateinit var viewModel: DashboardViewModel

    private val coin1 = AssetInfoData("coin1", 1, "+1.0 %")
    private val coin2 = AssetInfoData("coin2", 2, "-10.0 %")

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = DashboardViewModel(exchangeUseCase, coinUseCase)
    }

    @Test
    fun `when clickedExchanges is called, exchangeDataSelected is updated`() =
        runTest {
            // Arrange
            val mockExchangeList = listOf(coin1, coin2)
            coEvery { exchangeUseCase.getTopTenExchangeAssets() } returns flow {
                emit(CoinCapRepositoryResult.Success(mockExchangeList))
            }

            val observer: Observer<List<AssetInfoData>> = mockk(relaxed = true)
            viewModel.exchangeDataSelected.observeForever(observer)

            // Act
            viewModel.clickedExchanges()

            coVerify(exactly = 1) { exchangeUseCase.getTopTenExchangeAssets() }

            // Assert
            assertEquals(viewModel.exchangeDataSelected.value, mockExchangeList)
            viewModel.exchangeDataSelected.removeObserver(observer)
        }

    @Test
    fun `when clickedCoins is called, coinDataSelected is updated`() = runTest {
        // Arrange
        val mockCoinList = listOf(coin1, coin2)
        coEvery { coinUseCase.getTopTenCoinAssets() } returns flow {
            emit(CoinCapRepositoryResult.Success(mockCoinList))
        }

        val observer: Observer<List<AssetInfoData>> = mockk(relaxed = true)
        viewModel.coinDataSelected.observeForever(observer)

        // Act
        viewModel.getTopCoinAsset() // to set the value of topTenCoins
        viewModel.clickedCoins()

        // Then
        coVerify { viewModel.coinDataSelected.value = mockCoinList }
    }


    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
