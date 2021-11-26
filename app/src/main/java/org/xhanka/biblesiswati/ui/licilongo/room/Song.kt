package org.xhanka.biblesiswati.ui.licilongo.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LICILONGO")
class Song(
    @NonNull @field:ColumnInfo(name = "TITLE") var title: String,
    @NonNull @field:ColumnInfo(name = "SONG") var song: String
) {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_ID")
    var id = 0
}