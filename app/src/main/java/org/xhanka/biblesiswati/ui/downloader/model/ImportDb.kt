package org.xhanka.biblesiswati.ui.downloader.model

import com.google.gson.annotations.SerializedName


data class ImportDb(
    @SerializedName("dbName")
    val dbName: String,

    @SerializedName("verses")
    val verses: List<ImportVerse>
)

data class ImportVerse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("verse")
    val verse: String
)