package org.xhanka.biblesiswati.ui.notes.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static NotesDataBase INSTANCE;

    static NotesDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotesDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NotesDataBase.class, "note_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
