package uk.co.ht.base.data.dto

data class ExchangeResponse(
    val data : List<ExchangeObject>,
    val timestamp : Long
)