package uk.co.ht.base.data.dto

import com.google.gson.annotations.SerializedName

data class AssetsResponse (
    @SerializedName("data")
    val data: List<CoinObject>,
    @SerializedName("timestamp")
    val timestamp: Long
)