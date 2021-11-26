package org.xhanka.biblesiswati.ui.notes.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val database = NotesDataBase.getDatabase(application)
    private val noteDao = database.noteDao()
    var allNotes: LiveData<List<Note>> = noteDao.allNotes

    fun deleteNote(note: Note?) = viewModelScope.launch(Dispatchers.IO) {
        noteDao.delete(note)
    }

    fun updateNote(note: Note?) = viewModelScope.launch(Dispatchers.IO) {
        noteDao.update(note)
    }

    fun createNote(note: Note?) = viewModelScope.launch(Dispatchers.IO) {
        noteDao.insert(note)
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        noteDao.deleteAll()
    }
}