package uk.co.ht.base.data.dto

data class AssetsResponse (
    val data: List<CoinAsset>,
    val timestamp: Long
)