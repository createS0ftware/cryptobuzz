package uk.co.ht.cyberbuzz.common.utils

import androidx.annotation.DrawableRes
import uk.co.ht.cryptobuzz.R


enum class CryptoDrawable(val drawableResId: Int) {
    BITCOIN(R.drawable.bitcoin),
    ETHEREUM(R.drawable.ethereum),
    BINANCECOIN(R.drawable.binancecoin),
    BINANCE(R.drawable.binance),
    COINBASE(R.drawable.coinbasepro),
    CRYPTO(R.drawable.crypto),
    WHITEBIT(R.drawable.whitebit),
    DIGIFINEX(R.drawable.digifinex),
    KRAKEN(R.drawable.kraken),
    GATE(R.drawable.gate),
    BITMAX(R.drawable.bitmax),
    PROBIT(R.drawable.probit),
    BIBOX(R.drawable.bibox);

    companion object {
        fun fromString(name: String): CryptoDrawable? {
            return entries.find { it.name.equals(name, ignoreCase = true) }
        }
    }
}

@DrawableRes
fun getDrawableIdByName(name: String): Int {
    return CryptoDrawable.fromString(name)?.drawableResId ?: R.drawable.placeholder
}