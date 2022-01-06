package org.xhanka.biblesiswati.ui.licilongo.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(application: Application, database: SongDataBase) :
    AndroidViewModel(application) {
    // private val database = SongDataBase.getInstance(application)
    private val songDao = database.songDao()
    var songs: LiveData<List<Song>> = songDao.getAllSongs()
}