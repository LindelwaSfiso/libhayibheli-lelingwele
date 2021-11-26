package org.xhanka.biblesiswati.ui.main.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xhanka.biblesiswati.common.BibleVersion
import org.xhanka.biblesiswati.common.BibleVersion.NIV
import org.xhanka.biblesiswati.common.BibleVersion.SISWATI
import org.xhanka.biblesiswati.common.Constants
import org.xhanka.biblesiswati.ui.main.models.Verse
import org.xhanka.biblesiswati.ui.siswati_reference.RefBook

class BibleViewModel(application: Application) : AndroidViewModel(application) {
    val dataBase: BibleDataBase = BibleDataBase.getInstance(application)
    private var bibleVersion: LiveData<BibleVersion>

    private val booksDao = dataBase.booksDao()
    private val bibleDao = dataBase.bibleDao()
    var textSize: LiveData<Int>


    init {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
        textSize = MutableLiveData(sharedPreferences.getString("text_size", "18")?.toInt())

        val default = sharedPreferences.getString(Constants.SETTINGS_DATABASE, "niv") == "niv"
        bibleVersion = if (default) MutableLiveData(NIV) else MutableLiveData(SISWATI)
    }

    fun getVersion(): BibleVersion {
        return bibleVersion.value!!
    }

    fun getTextSizeValue(): Int {
        return textSize.value!!
    }

    fun getBooksByMode(mode: Int): LiveData<List<String>> {
        if (getVersion() == NIV)
            return booksDao.getNivBooksByMode(mode)
        return booksDao.getSiswatiBooksByMode(mode)
    }

    fun getChapterCount(bookName: String): LiveData<Int> {
        if (getVersion() == NIV)
            return booksDao.getNivBookCount(bookName)
        return booksDao.getSiswatiBookCount(bookName)
    }

    // todo: ths is not efficient, query what you need!!
    fun getBookVerses(bookName: String, chapter: Int): LiveData<List<Verse>> {
        val temp = getVersion()
        val range = Constants.getBookNumber(temp, bookName, chapter)

        if (temp == NIV)
            return bibleDao.getNivBookVerses(range[0], range[1])

        return bibleDao.getSiswatiBookVerses(range[0], range[1])
    }

    fun getAltVerse(verseId: Int): LiveData<String> {
        if (getVersion() == NIV)
            return bibleDao.getAltNivVerse(verseId)
        return bibleDao.getAltSiswatiVerse(verseId)
    }

    fun getBookName(english: String, siswati: String): String {
        if (getVersion() == NIV)
            return english
        return siswati
    }

    fun getRefBooks(): LiveData<List<RefBook>> {
        return booksDao.getBooks()
    }

    fun getAllFavorites(): LiveData<List<Verse>> {
        return bibleDao.getAllFavorites()
    }

    fun clearAllFavorites() = viewModelScope.launch(Dispatchers.IO) {
        bibleDao.clearAllFavorites()
    }

    fun addToFavorites(verseId: Int) = viewModelScope.launch(Dispatchers.IO) {
        bibleDao.addToFavorites(verseId)
    }

    fun removeFromFavorites(verseId: Int) = viewModelScope.launch(Dispatchers.IO) {
        bibleDao.removeFromFavorites(verseId)
    }

    fun isAddedToFavorites(verseId: Int): LiveData<Int?> {
        return bibleDao.isAddedToFavorites(verseId)
    }
}