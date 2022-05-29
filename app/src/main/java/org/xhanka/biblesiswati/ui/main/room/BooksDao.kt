package org.xhanka.biblesiswati.ui.main.room

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.xhanka.biblesiswati.ui.siswati_reference.RefBook

@Dao
interface BooksDao {
//    @Query("SELECT * FROM BOOKS;")
//    fun getAllBooks(): LiveData<List<Book>>

    @Query("SELECT NIV_BOOK,SISWATI_BOOK,ZULU_BOOK FROM BOOKS;")
    suspend fun getRefBooks(): List<RefBook>

    @Query(
        "SELECT NIV_BOOK,SISWATI_BOOK,ZULU_BOOK FROM BOOKS WHERE NIV_BOOK LIKE :search " +
                "OR SISWATI_BOOK LIKE :search"
    )
    suspend fun searchForRefBook(search: String): List<RefBook>

    @Query("SELECT NIV_BOOK FROM BOOKS WHERE TESTAMENT=:mode")
    fun getEnglishBooksByMode(mode: Int): Flow<List<String>>

    @Query("SELECT SISWATI_BOOK FROM BOOKS WHERE TESTAMENT=:mode")
    fun getSiswatiBooksByMode(mode: Int): Flow<List<String>>

    @Query("SELECT ZULU_BOOK FROM BOOKS WHERE TESTAMENT=:mode")
    fun getZuluBooksByMode(mode: Int): Flow<List<String>>

    @Query("SELECT CHAPTER_COUNT FROM BOOKS WHERE NIV_BOOK=:bookName")
    fun getEnglishBookCount(bookName: String): Flow<Int>

    @Query("SELECT CHAPTER_COUNT FROM BOOKS WHERE SISWATI_BOOK=:bookName")
    fun getSiswatiBookCount(bookName: String): Flow<Int>

    @Query("SELECT CHAPTER_COUNT FROM BOOKS WHERE ZULU_BOOK=:bookName")
    fun getZuluBookCount(bookName: String): Flow<Int>
}