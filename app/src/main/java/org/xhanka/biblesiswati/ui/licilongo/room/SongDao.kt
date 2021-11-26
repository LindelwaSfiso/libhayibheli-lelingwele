package org.xhanka.biblesiswati.ui.licilongo.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface SongDao {
    @Query("SELECT * from LICILONGO;")
    fun getAllSongs(): LiveData<List<Song>>

    @Query("SELECT TITLE FROM LICILONGO;")
    fun getAllSongTitles(): LiveData<List<String>>
}