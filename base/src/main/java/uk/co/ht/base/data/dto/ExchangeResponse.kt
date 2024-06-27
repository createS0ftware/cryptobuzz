package uk.co.ht.base.data.dto

import okhttp3.internal.connection.Exchange

data class ExchangeResponse(
    val data : List<Exchange>,
    val timestamp : Long
)