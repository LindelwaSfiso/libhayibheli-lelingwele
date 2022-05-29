package org.xhanka.biblesiswati.ui.downloader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import org.xhanka.biblesiswati.ui.main.room.BibleDataBase
import javax.inject.Inject

@HiltViewModel
class InstallExtraVersionViewModel @Inject constructor(
    val bibleDataBase: BibleDataBase
) : ViewModel() {
    private val bibleDao = bibleDataBase.bibleDao()

    val allVersion = bibleDao.getAllVersion().asLiveData(viewModelScope.coroutineContext)

    fun isInstalled(dbId: Int): Boolean = runBlocking {
        return@runBlocking bibleDao.isInstalled(dbId) == 1
    }

//    fun removeVersion(db: InstalledDb) = viewModelScope.launch {
//        bibleDataBase.withTransaction {
//            when (db.versionName) {
//                BibleVersion.KJV.versionName -> bibleDao.removeKjv()
//                BibleVersion.ZULU.versionName -> bibleDao.removeZulu()
//                BibleVersion.ASV.versionName -> bibleDao.removeAsv()
//                BibleVersion.BBE.versionName -> bibleDao.removeBbe()
//                BibleVersion.WBT.versionName -> bibleDao.removeWbt()
//                BibleVersion.YLT.versionName -> bibleDao.removeYlt()
//            }
//            bibleDao.removeInstalledVersion(db.id)
//        }
//    }
}