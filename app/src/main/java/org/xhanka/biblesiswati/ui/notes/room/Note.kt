package org.xhanka.biblesiswati.ui.notes.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
data class Note(
    @field:ColumnInfo(name = "note_title") var noteTitle: String, @field:ColumnInfo(
        name = "note_text"
    ) var noteText: String, @field:ColumnInfo(name = "note_timestamp") var dataAdded: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val note = other as Note
        return id == note.id && noteTitle == note.noteTitle &&
                noteText == note.noteText && dataAdded == note.dataAdded
    }

    override fun hashCode(): Int {
        return Objects.hash(id, noteTitle, noteText, dataAdded)
    }
}