package org.xhanka.biblesiswati.ui.licilongo.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class SongViewModel(application: Application) : AndroidViewModel(application) {
    private val database = SongDataBase.getInstance(application)
    private val songDao = database.songDao()
    var songs: LiveData<List<Song>> = songDao.getAllSongs()
}