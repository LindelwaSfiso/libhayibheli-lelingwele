package org.xhanka.biblesiswati.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.xhanka.biblesiswati.ui.licilongo.room.SongDataBase
import org.xhanka.biblesiswati.ui.main.room.BibleDataBase
import org.xhanka.biblesiswati.ui.notes.room.NotesDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val DATABASE_NAME = "bible.db"
    private const val assetDatabaseName = "databases/$DATABASE_NAME"

    private const val LICILONGO_DATABASE_NAME = "licilongo_database.db"
    private const val licilongoAssetDatabaseName = "databases/$LICILONGO_DATABASE_NAME"

    @Singleton
    @Provides
    fun providesBibleDataBase(
        @ApplicationContext context: Context
    ): BibleDataBase {
        return Room.databaseBuilder(
            context.applicationContext, BibleDataBase::class.java,
            DATABASE_NAME
        ).createFromAsset(assetDatabaseName).build()
    }


    @Singleton
    @Provides
    fun providesLicilongoDataBase(
        @ApplicationContext context: Context
    ): SongDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            SongDataBase::class.java,
            LICILONGO_DATABASE_NAME
        ).createFromAsset(licilongoAssetDatabaseName).build()
    }


    @Singleton
    @Provides
    fun providesNotesDataBase(
        @ApplicationContext context: Context
    ): NotesDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            NotesDataBase::class.java, "note_database"
        ).fallbackToDestructiveMigration().build()
        // Wipes and rebuilds instead of migrating
        // if no Migration object.
    }
}