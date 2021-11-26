package org.xhanka.biblesiswati.ui.main.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import org.xhanka.biblesiswati.ui.main.models.Book
import org.xhanka.biblesiswati.ui.siswati_reference.RefBook

@Dao
interface BooksDao {
    @Query("SELECT * FROM BOOKS;")
    fun getAllBooks(): LiveData<List<Book>>

    @Query("SELECT NIV_BOOK,SISWATI_BOOK FROM BOOKS;")
    fun getBooks(): LiveData<List<RefBook>>

    @Query("SELECT NIV_BOOK FROM BOOKS WHERE TESTAMENT=:mode")
    fun getNivBooksByMode(mode: Int): LiveData<List<String>>

    @Query("SELECT SISWATI_BOOK FROM BOOKS WHERE TESTAMENT=:mode")
    fun getSiswatiBooksByMode(mode: Int): LiveData<List<String>>

    @Query("SELECT CHAPTER_COUNT FROM BOOKS WHERE NIV_BOOK=:bookName")
    fun getNivBookCount(bookName: String): LiveData<Int>

    @Query("SELECT CHAPTER_COUNT FROM BOOKS WHERE SISWATI_BOOK=:bookName")
    fun getSiswatiBookCount(bookName: String): LiveData<Int>
}