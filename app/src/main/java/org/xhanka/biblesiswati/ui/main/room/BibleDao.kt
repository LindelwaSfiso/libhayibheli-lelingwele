package org.xhanka.biblesiswati.ui.main.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.xhanka.biblesiswati.ui.main.models.InstalledDb
import org.xhanka.biblesiswati.ui.main.models.Verse

@Dao
interface BibleDao {
    /* todo
    Optimize querying verses, try to get only required data only,
    and remove get alt versions, since they are already queried
    todo: test speed of getting every thing from database
    todo: try to get only relevant information
    */

    @Query("SELECT * FROM VERSES WHERE ID BETWEEN :startIndex AND :endIndex;")
    fun getVersesForBook(startIndex: Int, endIndex: Int): Flow<List<Verse>>

    @Query("SELECT NIV_VERSE FROM VERSES WHERE ID=:verseId;")
    fun getAltVernacularVerse(verseId: Int): LiveData<String>

    @Query("SELECT SISWATI_VERSE FROM VERSES WHERE ID=:verseId;")
    fun getAltEnglishVerse(verseId: Int): LiveData<String>

    @Query("SELECT * FROM VERSES WHERE FAVORITE IS NOT NULL AND DATE_ADDED IS NOT NULL;")
    fun getAllFavorites(): Flow<List<Verse>>

    @Query("UPDATE VERSES SET FAVORITE=1, DATE_ADDED=CURRENT_TIMESTAMP WHERE ID=:verseId;")
    suspend fun addToFavorites(verseId: Int)

    @Query("SELECT FAVORITE FROM VERSES WHERE ID=:verseId;")
    suspend fun isAddedToFavorites(verseId: Int): Int?

    @Query("UPDATE VERSES SET FAVORITE=NULL WHERE ID=:verseId;")
    suspend fun removeFromFavorites(verseId: Int)

    @Query("UPDATE VERSES SET FAVORITE = NULL WHERE FAVORITE IS NOT NULL;")
    suspend fun clearAllFavorites()


    // todo: Experimental, allow user to download versions
    // todo: consider moving this to a separate downloadDao

    @Query("UPDATE VERSES SET KJV_VERSE = :verse WHERE ID = :verseId;")
    suspend fun insertKjvVersion(verse: String, verseId: Int)

    @Query("UPDATE VERSES SET ASV_VERSE = :verse WHERE ID = :verseId;")
    suspend fun insertAsvVersion(verse: String, verseId: Int)

    @Query("UPDATE VERSES SET ZULU_VERSE = :verse WHERE ID = :verseId;")
    suspend fun insertZuluVersion(verse: String, verseId: Int)

    @Query("UPDATE VERSES SET BBE_VERSE = :verse WHERE ID = :verseId;")
    suspend fun insertBbeVersion(verse: String, verseId: Int)

    @Query("UPDATE VERSES SET YLT_VERSE = :verse WHERE ID = :verseId;")
    suspend fun insertYltVersion(verse: String, verseId: Int)

    @Query("UPDATE VERSES SET WBT_VERSE = :verse WHERE ID = :verseId;")
    suspend fun insertWbtVersion(verse: String, verseId: Int)

//    @Query("UPDATE VERSES SET KJV_VERSE = NULL;")
//    suspend fun removeKjv()
//
//    @Query("UPDATE VERSES SET ASV_VERSE = NULL;")
//    suspend fun removeAsv()
//
//    @Query("UPDATE VERSES SET BBE_VERSE = NULL;")
//    suspend fun removeBbe()
//
//    @Query("UPDATE VERSES SET ZULU_VERSE = NULL;")
//    suspend fun removeZulu()
//
//    @Query("UPDATE VERSES SET WBT_VERSE = NULL;")
//    suspend fun removeWbt()
//
//    @Query("UPDATE VERSES SET YLT_VERSE = NULL;")
//    suspend fun removeYlt()

    // Installed Versions section

    // run this on main thread since we need results immediately
    @Query("SELECT * FROM INSTALLED_VERSIONS WHERE IS_INSTALLED = 1")
    suspend fun getInstalledVersions(): List<InstalledDb>

    @Query("SELECT * FROM INSTALLED_VERSIONS ORDER BY IS_INSTALLED DESC;")
    fun getAllVersion(): Flow<List<InstalledDb>>

    @Query("SELECT IS_INSTALLED FROM INSTALLED_VERSIONS WHERE ID = :versionId;")
    suspend fun isInstalled(versionId: Int): Int

    @Query("UPDATE INSTALLED_VERSIONS SET IS_INSTALLED = 1 WHERE ID = :versionId;")
    suspend fun versionFullyInstalled(versionId: Int)

//    @Query("UPDATE INSTALLED_VERSIONS SET IS_INSTALLED = 0 WHERE ID = :versionId;")
//    suspend fun removeInstalledVersion(versionId: Int)
}