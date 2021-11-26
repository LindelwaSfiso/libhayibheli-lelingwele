package org.xhanka.biblesiswati.ui.main.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.xhanka.biblesiswati.ui.main.models.Verse

@Dao
interface BibleDao {
    @Query("SELECT * FROM VERSES WHERE ID BETWEEN :startIndex AND :endIndex;")
    fun getNivBookVerses(startIndex: Int, endIndex: Int): LiveData<List<Verse>>

    @Query("SELECT * FROM VERSES WHERE ID BETWEEN :startIndex AND :endIndex;")
    fun getSiswatiBookVerses(startIndex: Int, endIndex: Int): LiveData<List<Verse>>

    @Query("SELECT NIV_VERSE FROM VERSES WHERE ID=:verseId;")
    fun getAltSiswatiVerse(verseId: Int): LiveData<String>

    @Query("SELECT SISWATI_VERSE FROM VERSES WHERE ID=:verseId;")
    fun getAltNivVerse(verseId: Int): LiveData<String>

    @Query("SELECT * FROM VERSES WHERE FAVORITE IS NOT NULL AND DATE_ADDED IS NOT NULL")
    fun getAllFavorites(): LiveData<List<Verse>>

    @Query("UPDATE VERSES SET FAVORITE=1, DATE_ADDED=CURRENT_TIMESTAMP WHERE ID=:verseId")
    suspend fun addToFavorites(verseId: Int)

    @Query("SELECT FAVORITE FROM VERSES WHERE ID=:verseId")
    fun isAddedToFavorites(verseId: Int): LiveData<Int?>

    @Query("UPDATE VERSES SET FAVORITE=NULL WHERE ID=:verseId")
    suspend fun removeFromFavorites(verseId: Int)

    @Query("UPDATE VERSES SET FAVORITE = NULL WHERE FAVORITE IS NOT NULL")
    suspend fun clearAllFavorites()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun modifyVerse(verse: Verse)

}