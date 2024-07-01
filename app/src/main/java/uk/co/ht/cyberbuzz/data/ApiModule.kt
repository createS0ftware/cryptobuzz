package uk.co.ht.cyberbuzz.data

import ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.ht.base.common.BuildVariableProvider
import uk.co.ht.base.data.network.APIService
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun getApiService(buildVariableProvider: BuildVariableProvider): APIService {
        val interceptor = HttpLoggingInterceptor()

        val builder = Retrofit.Builder()
            .baseUrl(buildVariableProvider.getAPIBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())

        if (buildVariableProvider.isDebug()) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
            .build()

        val retrofit = builder.client(client).build()

        return retrofit.create(APIService::class.java)
    }

}