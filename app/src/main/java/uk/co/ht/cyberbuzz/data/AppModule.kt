package uk.co.ht.cyberbuzz.data

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import uk.co.ht.base.common.BuildVariableProvider
import uk.co.ht.base.data.network.APIService
import uk.co.ht.base.data.network.CoinCapRepositoryImpl
import uk.co.ht.base.domain.repository.CoinCapRepository
import uk.co.ht.cyberbuzz.CyberBuzzApplication
import uk.co.ht.cyberbuzz.common.BuildVariableProviderImpl
import uk.co.ht.cyberbuzz.domain.usecases.CoinUseCase
import uk.co.ht.cyberbuzz.domain.usecases.ExchangeUseCase

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun getBuildVariableProvider(): BuildVariableProvider {
        return BuildVariableProviderImpl()
    }

    @Provides
    fun provideRepository (apiService: APIService): CoinCapRepository {
        return CoinCapRepositoryImpl(apiService)
    }

    @Provides
    fun provideExchangeUseCase(repository: CoinCapRepository): ExchangeUseCase {
        return ExchangeUseCase(repository)
    }

    @Provides
    fun provideCoinUseCase(repository: CoinCapRepository): CoinUseCase {
        return CoinUseCase(repository)
    }
}