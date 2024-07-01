package uk.co.ht.cyberbuzz.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class ExchangeData(
    val exchangeName: String,
    val rank: Int,
    val volumeUsd: String
)