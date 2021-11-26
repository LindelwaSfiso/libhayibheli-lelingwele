package org.xhanka.biblesiswati.ui.licilongo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class SongDataBase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        private const val DATABASE_NAME = "licilongo_database.db"
        private const val assetDatabaseName = "databases/licilongo_database.db"

        @Volatile
        private var instance: SongDataBase? = null

        fun getInstance(context: Context): SongDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): SongDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                SongDataBase::class.java,
                DATABASE_NAME
            )
                .createFromAsset(assetDatabaseName)
                .build()
        }
    }
}