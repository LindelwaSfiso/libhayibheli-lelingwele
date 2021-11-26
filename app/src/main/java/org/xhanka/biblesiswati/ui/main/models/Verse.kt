package org.xhanka.biblesiswati.ui.main.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.xhanka.biblesiswati.ui.main.room.Converter
import java.text.DateFormat
import java.util.*

@Entity(tableName = "VERSES")
@TypeConverters(Converter::class)
class Verse(
    @field:ColumnInfo(name = "TESTAMENT") var testament: Int,
    @field:ColumnInfo(name = "NIV_BOOK") var nivBook: String,
    @field:ColumnInfo(name = "SISWATI_BOOK") var siswatiBook: String,
    @field:ColumnInfo(name = "CHAPTER") var chapter: Int,
    @field:ColumnInfo(name = "VERSE_N") var verseNum: Int,
    @field:ColumnInfo(name = "NIV_VERSE") var nivVerse: String,
    @field:ColumnInfo(name = "SISWATI_VERSE") var siswatiVerse: String,
    @field:ColumnInfo(name = "FAVORITE") var favorite: Int?,
    @field:ColumnInfo(name = "DATE_ADDED") var date: Date?,
) {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    var id = 0

    fun isAddedToFavorites(): Boolean {
        return favorite == 1
    }

    fun setFav(boolean: Boolean) {
        favorite = if (boolean) 1 else 0
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