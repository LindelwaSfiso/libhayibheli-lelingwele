package org.xhanka.biblesiswati.common;

import java.util.Locale;

public class Constants {
    public static final String COLUMN_NIV_BOOK = "NIV_BOOK";
    public static final String COLUMN_SISWATI_BOOK = "SISWATI_BOOK";
    public static final String COLUMN_NIV_VERSE = "NIV_VERSE";
    public static final String COLUMN_SISWATI_VERSE = "SISWATI_VERSE";

    public static final String SETTINGS_DATABASE = "database";

    static final String[] BOOKS_NIV = new String[]{"Genesis", "Exodus", "Leviticus", "Numbers",
            "Deuteronomy", "Joshua", "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings",
            "1 Chronicles", "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms",
            "Proverbs", "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations",
            "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum",
            "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi", "Matthew", "Mark", "Luke",
            "John", "Acts", "Romans", "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians",
            "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy",
            "2 Timothy", "Titus", "Philemon", "Hebrews", "James", "1 Peter", "2 Peter", "1 John",
            "2 John", "3 John", "Jude", "Revelation"};

    static final String[] BOOKS_SISWATI = new String[]{"Genesisi", "Eksodusi", "Levithikusi",
            "Numeri", "Dutheronomi", "Joshuwa", "Tikhulu", "Ruthe", "1 Samuweli", "2 Samuweli",
            "1 Emakhosi", "2 Emakhosi", "1 Tikhronike", "2 Tikhronike", "Ezra", "Nehemiya", "Esta",
            "Jobe", "Tihlabelelo", "Taga", "Umshumayeli", "Ingoma Yetingoma", "Isaya", "Jeremiya",
            "Sililo", "Hezekeli", "Danyela", "Hoseya", "Joweli", "Amose", "Obadiya", "Jona", "Mikha",
            "Nahume", "Habakhuki", "Zefaniya", "Hagayi", "Zakhariya", "Malakhi", "Matewu", "Makho",
            "Lukha", "Johane", "Imisebenti yebaPhostoli", "KubaseRoma", "1 kubaseKorinthe",
            "2 kubaseKorinthe", "KubaseGalathiya", "Kubase-Efesu", "KubaseFiliphi", "KubaseKholose",
            "1 kubaseThesalonika", "2 kubaseThesalonika", "1 kuThimothi", "2 kuThimothi",
            "kuThithusi", "kuFilemoni", "KumaHebheru", "YaJakobe", "1 yaPhetro", "2 yaPhetro",
            "1 yaJohane", "2 yaJohane", "3 yaJohane", "YaJuda", "Sembulo"};

    static public int[] getBookNumber(BibleVersion version, String bookName, int chapterNum) {
        // function to retrieve a specific chapter from a specific book
        // example, get genesis one
        // function returns the range index
        // such that query becomes
        // select * from bible where id between [startindex] and [endindex]

        String bookName_;
        if (version == BibleVersion.NIV)
            bookName_ = String.format(Locale.ENGLISH, "%02d",
                    search(BOOKS_NIV, bookName) + 1
            );
        else
            bookName_ = String.format(Locale.ENGLISH, "%02d",
                    search(BOOKS_SISWATI, bookName) + 1
            );

        return new int[]{
                Integer.parseInt(String.format(Locale.ENGLISH, "%s%03d000", bookName_, chapterNum)),
                Integer.parseInt(String.format(Locale.ENGLISH, "%s%03d000", bookName_, chapterNum + 1))
        };
    }

    /**
     * Function that searches unsorted array [source: www.geeksforgeeks.com]
     *
     * @param array unsorted array
     * @param key   key value
     * @return the index position
     */
    private static int search(String[] array, String key) {
        int length = array.length;

        if (array[length - 1].equals(key))
            return length - 1;

        String backup = array[length - 1];
        array[length - 1] = key;

        for (int i = 0; ; i++) {
            if (array[i].equals(key)) {
                array[length - 1] = backup;
                if (i < length - 1)
                    return i;
                return -1;
            }
        }
    }
}
