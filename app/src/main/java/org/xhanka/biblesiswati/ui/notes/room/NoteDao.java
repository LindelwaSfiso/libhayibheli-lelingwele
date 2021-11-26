package org.xhanka.biblesiswati.ui.notes.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("DELETE FROM Note")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Query("SELECT * from Note ORDER BY note_timestamp DESC")
    LiveData<List<Note>> getAllNotes();

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
