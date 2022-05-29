package org.xhanka.biblesiswati.common

/**
 * Utility enum class for passing data from InstallFragment to ImportVersionService
 * This is a patch since Android has a limit on the size of data sent in Intent as Parcelable
 * Without this, the code raises a PoolException
 */
enum class DATA(var jsonText: String? = null) {
    JSON;

    fun seText(t: String?) {
        jsonText = t
    }

    fun hasData(): Boolean {
        return jsonText != null
    }
}