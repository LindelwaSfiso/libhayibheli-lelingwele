package org.xhanka.biblesiswati.ui.main.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BOOKS")
class Book(
    @field:ColumnInfo(name = "TESTAMENT") var testament: Int,
    @field:ColumnInfo(name = "CHAPTER_COUNT") var chapterCount: Int,
    @field:ColumnInfo(name = "NIV_BOOK") var nivBook: String,
    @field:ColumnInfo(name = "SISWATI_BOOK") var siswatiBook: String,
    @field:ColumnInfo(name = "ZULU_BOOK") var zuluBook: String
) {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    var id = 0
}