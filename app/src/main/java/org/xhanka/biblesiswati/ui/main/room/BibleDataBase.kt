package org.xhanka.biblesiswati.ui.main.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.xhanka.biblesiswati.ui.main.models.Book
import org.xhanka.biblesiswati.ui.main.models.InstalledDb
import org.xhanka.biblesiswati.ui.main.models.Verse

@Database(
    entities = [Book::class, Verse::class, InstalledDb::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class BibleDataBase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
    abstract fun bibleDao(): BibleDao

    /* companion object {
        private const val DATABASE_NAME = "bible.db"
        private const val assetDatabaseName = "databases/${DATABASE_NAME}"

        @Volatile
        private var instance: BibleDataBase? = null

        fun getInstance(context: Context): BibleDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): BibleDataBase {
            return Room.databaseBuilder(
                context.applicationContext, BibleDataBase::class.java,
                DATABASE_NAME
            ).createFromAsset(assetDatabaseName).build()
        }
    }*/
}