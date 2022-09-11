package org.xhanka.biblesiswati.common

import android.content.Context
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import org.xhanka.biblesiswati.ui.downloader.model.ImportVerse
import org.xhanka.biblesiswati.ui.main.models.InstalledDb
import org.xhanka.biblesiswati.ui.main.models.Verse
import org.xhanka.biblesiswati.ui.main.room.BibleDao
import org.xhanka.biblesiswati.ui.notes.room.Note
import org.xhanka.biblesiswati.ui.siswati_reference.RefBook
import java.util.*


fun TextView.setTextSizeSp(float: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, float)
}

fun TextView.setFavVerseTextColor(@ColorRes color: Int) {
    setTextColor(ResourcesCompat.getColor(this.context.resources, color, null))
}

/**
 * Utility function to get the related verse according to selected verse
 * @param bibleVersion the currently selected bible version
 * @return verse return the verseText of the current
 */
fun Verse.getVerse(bibleVersion: BibleVersion): String {
    return when (bibleVersion) {
        BibleVersion.NIV -> this.nivVerse
        BibleVersion.SISWATI -> this.siswatiVerse
        BibleVersion.KJV -> this.kjvVerse.toString()
        BibleVersion.ZULU -> this.zuluVerse.toString()
        BibleVersion.ASV -> this.asvVerse.toString()
        BibleVersion.BBE -> this.bbeVerse.toString()
        BibleVersion.WBT -> this.wbtVerse.toString()
        BibleVersion.YLT -> this.yltVerse.toString()
    }
}


fun BibleVersion.isEnglish(): Boolean {
    return when (this) {
        BibleVersion.SISWATI, BibleVersion.ZULU -> false
        else -> true
    }
}

fun BibleVersion.isSiswati(): Boolean {
    return when (this) {
        BibleVersion.SISWATI -> true
        else -> false
    }
}

fun BibleVersion.isZulu(): Boolean {
    return when (this) {
        BibleVersion.ZULU -> true
        else -> false
    }
}

fun Verse.getBookAndVerse(
    version: BibleVersion,
    assignTitleAndVerse: (title: String, verse: String) -> Unit
) {
    var title = ""
    var verse = ""

    when (version) {
        BibleVersion.NIV -> {
            title = this.nivBook
            verse = this.nivVerse
        }
        BibleVersion.SISWATI -> {
            title = this.siswatiBook
            verse = this.siswatiVerse
        }
        BibleVersion.KJV -> {
            title = this.nivBook
            verse = this.kjvVerse.toString()
        }
        BibleVersion.ASV -> {
            title = this.nivBook
            verse = this.asvVerse.orEmpty()
        }
        BibleVersion.ZULU -> {
            title = this.zuluBook
            verse = this.zuluVerse.orEmpty()
        }
        BibleVersion.BBE -> {
            title = this.nivBook
            verse = this.bbeVerse.orEmpty()
        }
        BibleVersion.WBT -> {
            title = this.nivBook
            verse = this.wbtVerse.orEmpty()
        }
        BibleVersion.YLT -> {
            title = this.nivBook
            verse = this.yltVerse.orEmpty()
        }
    }

    assignTitleAndVerse(title, verse)
}

suspend fun String.installNewVersion(
    bibleDao: BibleDao,
    importVerses: List<ImportVerse>,
    updateProgress: (progress: Int) -> Unit
) {
    when (this) {
        BibleVersion.KJV.versionName -> {
            importVerses.forEachIndexed { index, (id, verse) ->
                bibleDao.insertKjvVersion(verse, id)
                updateProgress(index)
            }
            bibleDao.versionFullyInstalled(BibleVersion.KJV.versionId)
        }
        BibleVersion.ASV.versionName -> {
            importVerses.forEachIndexed { index, (id, verse) ->
                bibleDao.insertAsvVersion(verse, id)
                updateProgress(index)
            }
            bibleDao.versionFullyInstalled(BibleVersion.ASV.versionId)
        }
        BibleVersion.ZULU.versionName -> {
            importVerses.forEachIndexed { index, (id, verse) ->
                bibleDao.insertZuluVersion(verse, id)
                updateProgress(index)
            }
            bibleDao.versionFullyInstalled(BibleVersion.ZULU.versionId)
        }
        BibleVersion.BBE.versionName -> {
            importVerses.forEachIndexed { index, (id, verse) ->
                bibleDao.insertBbeVersion(verse, id)
                updateProgress(index)
            }
            bibleDao.versionFullyInstalled(BibleVersion.BBE.versionId)
        }
        BibleVersion.YLT.versionName -> {
            importVerses.forEachIndexed { index, (id, verse) ->
                bibleDao.insertYltVersion(verse, id)
                updateProgress(index)
            }
            bibleDao.versionFullyInstalled(BibleVersion.YLT.versionId)
        }
        BibleVersion.WBT.versionName -> {
            importVerses.forEachIndexed { index, (id, verse) ->
                bibleDao.insertWbtVersion(verse, id)
                updateProgress(index)
            }
            bibleDao.versionFullyInstalled(BibleVersion.WBT.versionId)
        }
    }
}


class Utils {
    companion object {

        const val SETTINGS_DARK_MODE = "dark_mode"
        const val SETTINGS_BIBLE_VERSION = "database"
        const val SETTINGS_TEXT_SIZE = "text_size"
        const val CONTACT_US_EMAIL = "sfisodummy@gmail.com"
        const val EMAIL_SUBJECT = "Bible Siswati App: Feedback"

        val STRINGS_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }

        val VERSE_COMPARATOR = object : DiffUtil.ItemCallback<Verse>() {
            override fun areItemsTheSame(oldItem: Verse, newItem: Verse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Verse, newItem: Verse): Boolean {
                // either use data class or use .equals() method to compare contents
                return oldItem == newItem
            }
        }

        val NOTES_COMPARATOR = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }

        val REF_COMPARATOR = object : DiffUtil.ItemCallback<RefBook>() {
            override fun areItemsTheSame(oldItem: RefBook, newItem: RefBook): Boolean {
                return oldItem.NIV_BOOK == newItem.NIV_BOOK
            }

            override fun areContentsTheSame(oldItem: RefBook, newItem: RefBook): Boolean {
                return oldItem == newItem
            }
        }

        val INSTALLED_DB_COMPARATOR = object : DiffUtil.ItemCallback<InstalledDb>() {
            override fun areItemsTheSame(oldItem: InstalledDb, newItem: InstalledDb): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: InstalledDb, newItem: InstalledDb): Boolean {
                return oldItem == newItem
            }

        }


        private fun getDarkMode(context: Context?): String {
            context?.let {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(it)
                return sharedPreferences.getString(SETTINGS_DARK_MODE, "0").toString()
            } ?: run {
                return "0"
            }
        }

        fun setDarkMode(darkMode: String) {
            when (darkMode) {
                "0" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                "1" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "2" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        fun toggleDarkMode(context: Context?) {
            context?.let {
                setDarkMode(getDarkMode(it))
            }
        }

        fun mapDbVersions(versionName: String?): BibleVersion {
            return versionName?.let {
                BibleVersion.values().first {
                    it.versionName.lowercase() == versionName
                }
            } ?: run {
                BibleVersion.SISWATI
            }
        }

        fun mapDbVersionsUpper(versionName: String?): BibleVersion {
            return versionName?.let {
                BibleVersion.values().first {
                    it.versionName == versionName
                }
            } ?: run {
                BibleVersion.SISWATI
            }
        }

        /**
         * Function that return the start and end indices for a given book chapter.
         * e.g. if we want Genesis 1, this function will return 100100000
         * The First two for book, second three for chapter and last three for verseNum
         * (which is 000) by default.
         *
         * Generated SQLite query becomes:
         * <em>select * from bible where id between startIndex and endIndex</em>
         *
         * @param version current selected bible version
         * @param bookName bookName of the selected book
         * @param chapterNum current chapter
         *
         * @return returns a pair (firstIndex, lastIndex)
         */
        fun getBookChapterIndices(
            version: BibleVersion,
            bookName: String,
            chapterNum: Int
        ): Pair<Int, Int> {
            val bookIndex: String = when {
                version.isSiswati() -> {
                    String.format(Locale.ENGLISH, "%02d", searchIndex(SISWATI_BOOKS, bookName))
                }
                version.isZulu() -> {
                    String.format(Locale.ENGLISH, "%02d", searchIndex(ZULU_BOOKS, bookName))
                }
                else -> String.format(Locale.ENGLISH, "%02d", searchIndex(NIV_BOOKS, bookName))
            }

//            val bookIndex: String = if (version.isEnglish())
//                String.format(Locale.ENGLISH, "%02d", searchIndex(NIV_BOOKS, bookName))
//            else String.format(Locale.ENGLISH, "%02d", searchIndex(SISWATI_BOOKS, bookName))

            return Pair(
                String.format(Locale.ENGLISH, "%s%03d000", bookIndex, chapterNum).toInt(),
                String.format(Locale.ENGLISH, "%s%03d000", bookIndex, chapterNum + 1).toInt()
            )
        }

        private fun searchIndex(booksArray: Array<String>, key: String): Int {
            return booksArray.indexOf(key) + 1
        }

        private val NIV_BOOKS = arrayOf(
            "Genesis", "Exodus", "Leviticus", "Numbers",
            "Deuteronomy", "Joshua", "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings",
            "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms",
            "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations",
            "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum",
            "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke",
            "John", "Acts", "Romans", "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians",
            "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy",
            "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter", "1 John",
            "2 John", "3 John", "Jude", "Revelation"
        )

        private val SISWATI_BOOKS = arrayOf(
            "Genesisi", "Eksodusi", "Levithikusi", "Numeri", "Dutheronomi",
            "Joshuwa", "Tikhulu", "Ruthe", "1 Samuweli", "2 Samuweli", "1 Emakhosi", "2 Emakhosi",
            "1 Tikhronike", "2 Tikhronike", "Ezra", "Nehemiya", "Esta", "Jobe", "Tihlabelelo",
            "Taga", "Umshumayeli", "Ingoma Yetingoma", "Isaya", "Jeremiya", "Sililo", "Hezekeli",
            "Danyela", "Hoseya", "Joweli", "Amose", "Obadiya", "Jona", "Mikha", "Nahume",
            "Habakhuki", "Zefaniya", "Hagayi", "Zakhariya", "Malakhi", "Matewu", "Makho",
            "Lukha", "Johane", "Imisebenti yebaPhostoli", "KubaseRoma", "1 kubaseKorinthe",
            "2 kubaseKorinthe", "KubaseGalathiya", "Kubase-Efesu", "KubaseFiliphi", "KubaseKholose",
            "1 kubaseThesalonika", "2 kubaseThesalonika", "1 kuThimothi", "2 kuThimothi",
            "kuThithusi", "kuFilemoni", "KumaHebheru", "YaJakobe", "1 yaPhetro", "2 yaPhetro",
            "1 yaJohane", "2 yaJohane", "3 yaJohane", "YaJuda", "Sembulo"
        )

        private val ZULU_BOOKS = arrayOf(
            "Genesise", "Eksodusi", "Levitikusi", "Numeri",
            "Duteronomi", "Joshuwa", "AbAhluleli", "Ruthe", "1 Samuweli", "2 Samuweli",
            "1 AmaKhosi", "2 AmaKhosi", "1 IziKronike", "2 IziKronike", "Ezra", "Nehemiya",
            "Esteri", "Jobe", "AmaHubo", "IzAga", "UmShumayeli", "IsiHlabelelo SeziHlabelelo",
            "Isaya", "Jeremiya", "IsiLilo", "Hezekeli", "Daniyeli", "Hoseya", "Joweli", "Amose",
            "Obadiya", "Jona", "Mika", "Nahume", "Habakuki", "Zefaniya", "Hagayi", "Zakariya",
            "Malaki", "Mathewu", "Marku", "Luka", "Johane", "IzEnzo", "AmaRoma", "1 Korinte",
            "2 Korinte", "Galathiya", "Efesu", "Filipi", "Kolose", "1 Thesalonika", "2 Thesalonika",
            "1 Thimothewu", "2 Thimothewu", "KuThithu", "KuFilemoni", "KumaHeberu", "EkaJakobe",
            "1 Petru", "2 Petru", "1 Johane", "2 Johane", "3 Johane", "EkaJuda", "IsAmbulo"
        )
    }
}

//if (array[length - 1].equals(key))
//return length - 1;
//
//String backup = array[length - 1];
//array[length - 1] = key;
//
//for (int i = 0; ; i++) {
//    if (array[i].equals(key)) {
//        array[length - 1] = backup;
//        if (i < length - 1)
//            return i;
//        return -1;
//    }
//}
