package uk.co.ht.cyberbuzz.domain.models

import androidx.compose.runtime.Immutable
import okhttp3.internal.connection.Exchange
import uk.co.ht.base.data.dto.CoinAsset

@Immutable
data class DashboardItemData (
    val id : String,
    var name : String,
    val coinAsset: CoinData? = null,
    val exchange: Exchange? = null,
    val imageResource: Int
    )