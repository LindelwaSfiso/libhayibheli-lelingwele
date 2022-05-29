package org.xhanka.biblesiswati.ui.main.room

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.xhanka.biblesiswati.common.BibleVersion
import org.xhanka.biblesiswati.common.Utils
import org.xhanka.biblesiswati.common.isSiswati
import org.xhanka.biblesiswati.common.isZulu
import org.xhanka.biblesiswati.ui.main.models.InstalledDb
import org.xhanka.biblesiswati.ui.main.models.Verse
import org.xhanka.biblesiswati.ui.siswati_reference.RefBook
import javax.inject.Inject

@HiltViewModel
class BibleViewModel @Inject constructor(
    application: Application,
    dataBase: BibleDataBase,
    repository: BibleRepository
) : AndroidViewModel(application) {

    private val booksDao = dataBase.booksDao()
    private val bibleDao = dataBase.bibleDao()

    private var _bibleVersion: MutableLiveData<BibleVersion> =
        MutableLiveData(repository.defaultDatabase)
    val bibleVersion: LiveData<BibleVersion> = _bibleVersion

    private var _textSize: MutableLiveData<Int> = MutableLiveData(repository.textSize)
    private val textSize: LiveData<Int> = _textSize

    fun getVersion(): BibleVersion {
        return bibleVersion.value!!
    }

    private fun setVersion(version: BibleVersion) = viewModelScope.launch {
        _bibleVersion.postValue(version)
    }

    fun setVersion(version: String) = viewModelScope.launch {
        setVersion(Utils.mapDbVersions(version))
    }

    fun getTextSizeValue(): Int {
        return textSize.value!!
    }

    fun setTextSizeValue(newTextSize: String?) = viewModelScope.launch {
        _textSize.postValue(newTextSize?.toInt())
    }

    fun getBooksByMode(mode: Int): LiveData<List<String>> {
        val temp = getVersion()
        return when {
            temp.isSiswati() -> booksDao.getSiswatiBooksByMode(mode).asLiveData()
            temp.isZulu() -> booksDao.getZuluBooksByMode(mode).asLiveData()
            else -> return booksDao.getEnglishBooksByMode(mode).asLiveData()
        }
    }


    fun getChapterCount(bookName: String): LiveData<Int> {
        val temp = getVersion()
        return when {
            temp.isSiswati() -> booksDao.getSiswatiBookCount(bookName).asLiveData()
            temp.isZulu() -> booksDao.getZuluBookCount(bookName).asLiveData()
            else -> booksDao.getEnglishBookCount(bookName).asLiveData()
        }
    }

    // todo: filter only tables required
    fun getBookVerses(bookName: String, chapter: Int): LiveData<List<Verse>> {
        val temp = getVersion()
        val range = Utils.getBookChapterIndices(temp, bookName, chapter)
        // Log.d("TAG", "$bookName $chapter $temp ${range.first} ${range.second}")

        return bibleDao.getVersesForBook(range.first, range.second).asLiveData()
    }

    fun getBookName(english: String, siswati: String, zulu: String): String {
        return when {
            getVersion().isSiswati() -> siswati
            getVersion().isZulu() -> zulu
            else -> english
        }
    }


    fun getAllFavorites(): LiveData<List<Verse>> {
        return bibleDao.getAllFavorites().asLiveData()
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

    fun isAddedToFavorites(verseId: Int): Int? = runBlocking {
        return@runBlocking bibleDao.isAddedToFavorites(verseId)
    }

    private var _books: MutableLiveData<List<RefBook>> = MutableLiveData()
    val books: LiveData<List<RefBook>> = _books

    init {
        getRefBooks()
    }

    fun getRefBooks() = viewModelScope.launch {
        _books.postValue(booksDao.getRefBooks())
    }

    fun searchRefBooks(search: String) = viewModelScope.launch {
        _books.postValue(booksDao.searchForRefBook(search))
    }

    val installedVersions: List<InstalledDb> = runBlocking {
        return@runBlocking bibleDao.getInstalledVersions()
    }

//    fun isInstalled(verseId: Int): Int? = runBlocking {
//        return@runBlocking bibleDao.isAddedToFavorites(verseId)
//    }
}