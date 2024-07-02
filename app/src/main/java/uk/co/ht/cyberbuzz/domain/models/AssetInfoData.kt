package uk.co.ht.cryptobuzz.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class AssetInfoData(
    val assetName: String,
    val assetRank: Int,
    val assetValue: String
)