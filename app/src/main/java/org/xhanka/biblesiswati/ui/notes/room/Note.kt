package org.xhanka.biblesiswati.ui.notes.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @field:ColumnInfo(name = "note_title") var noteTitle: String, @field:ColumnInfo(
        name = "note_text"
    ) var noteText: String, @field:ColumnInfo(name = "note_timestamp") var dataAdded: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable
