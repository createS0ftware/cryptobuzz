package uk.co.ht.cyberbuzz.common

import uk.co.ht.base.common.BuildVariableProvider
import uk.co.ht.cyberbuzz.BuildConfig

class BuildVariableProviderImpl: BuildVariableProvider {
    override fun getAPIBaseUrl(): String {
        //return BuildConfig.BASE_URL;
        return "https://api.coincap.io/v2/";
    }

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG;
    }

    override fun isRelease(): Boolean {
        return !BuildConfig.DEBUG;
    }

}