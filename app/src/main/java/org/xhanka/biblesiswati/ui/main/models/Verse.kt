package org.xhanka.biblesiswati.ui.main.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import org.xhanka.biblesiswati.ui.main.room.Converter
import java.text.DateFormat
import java.util.*

@Parcelize
@Entity(tableName = "VERSES")
@TypeConverters(Converter::class)
data class Verse(
    @field:ColumnInfo(name = "TESTAMENT") var testament: Int,
    @field:ColumnInfo(name = "NIV_BOOK") var nivBook: String,
    @field:ColumnInfo(name = "SISWATI_BOOK") var siswatiBook: String,
    @field:ColumnInfo(name = "ZULU_BOOK") var zuluBook: String,
    @field:ColumnInfo(name = "CHAPTER") var chapter: Int,
    @field:ColumnInfo(name = "VERSE_N") var verseNum: Int,
    @field:ColumnInfo(name = "NIV_VERSE") var nivVerse: String,
    @field:ColumnInfo(name = "SISWATI_VERSE") var siswatiVerse: String,
    @field:ColumnInfo(name = "KJV_VERSE") var kjvVerse: String?,
    @field:ColumnInfo(name = "ASV_VERSE") var asvVerse: String?,
    @field:ColumnInfo(name = "BBE_VERSE") var bbeVerse: String?,
    @field:ColumnInfo(name = "WBT_VERSE") var wbtVerse: String?,
    @field:ColumnInfo(name = "YLT_VERSE") var yltVerse: String?,
    @field:ColumnInfo(name = "ZULU_VERSE") var zuluVerse: String?,
    @field:ColumnInfo(name = "FAVORITE") var favorite: Int?,
    @field:ColumnInfo(name = "DATE_ADDED") var date: Date?,
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable {


    fun isAddedToFavorites(): Boolean {
        return favorite == 1
    }

    fun getFormattedDate(): String {
        if (date != null)
            return DateFormat.getInstance().format(date!!)
        return "---"
    }

    fun getTitle(bookName: String): String {
        return "$bookName $chapter:$verseNum"
    }
}