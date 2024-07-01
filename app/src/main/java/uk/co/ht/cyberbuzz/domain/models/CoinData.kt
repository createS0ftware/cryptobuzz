package uk.co.ht.cryptobuzz.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class CoinData(
    val coinName: String,
    val percentChange: String,
    val imageResource: Int
)