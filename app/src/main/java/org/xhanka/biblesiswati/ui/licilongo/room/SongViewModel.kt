package org.xhanka.biblesiswati.ui.licilongo.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(application: Application, database: SongDataBase) :
    AndroidViewModel(application) {
    // todo: consider using ViewModel rather than AndroidViewModel
    private val songDao = database.songDao()
    private var _songs: MutableLiveData<List<Song>> = MutableLiveData()
    val songs: LiveData<List<Song>> = _songs

    init {
        getAllSongs()
    }

    fun getAllSongs() = viewModelScope.launch {
        _songs.postValue(songDao.getAllSongs())
    }

    fun searchForSong(searchWord: String) = viewModelScope.launch {
        _songs.postValue(songDao.searchForSong(searchWord))
    }

}