package uk.co.ht.cryptobuzz.common

import uk.co.ht.base.common.BuildVariableProvider
import uk.co.ht.cryptobuzz.BuildConfig

class BuildVariableProviderImpl: BuildVariableProvider {
    override fun getAPIBaseUrl(): String {
        return BuildConfig.BASE_URL;
        //return "https://api.coincap.io/v2/";
    }

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG;
    }

    override fun isRelease(): Boolean {
        return !BuildConfig.DEBUG;
    }

}