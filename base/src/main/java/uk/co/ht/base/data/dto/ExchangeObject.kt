package uk.co.ht.base.data.dto

data class ExchangeObject(
    val exchangeId: String,
    val exchangeUrl: String,
    val name: String,
    val percentTotalVolume: String,
    val rank: String,
    val socket: Boolean,
    val tradingPairs: String,
    val updated: Long,
    val volumeUsd: String
)