package uk.co.ht.base.data.network

import retrofit2.http.GET
import uk.co.ht.base.data.dto.AssetsResponse
import uk.co.ht.base.data.dto.ExchangeResponse

interface APIService {
    @GET("assets")
    suspend fun getAssets(): Result<AssetsResponse>

    @GET("exchanges")
    suspend fun getExchanges(): Result<ExchangeResponse>

}