package org.xhanka.biblesiswati.ui.licilongo.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SongDao {
    @Query("SELECT * from LICILONGO;")
    suspend fun getAllSongs(): List<Song>

    @Query("SELECT * FROM LICILONGO WHERE TITLE LIKE :search")
    suspend fun searchForSong(search: String): List<Song>
}