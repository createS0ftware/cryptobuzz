package uk.co.ht.base.common

interface BuildVariableProvider {
    fun getAPIBaseUrl(): String
    fun isDebug(): Boolean
    fun isRelease(): Boolean
}
