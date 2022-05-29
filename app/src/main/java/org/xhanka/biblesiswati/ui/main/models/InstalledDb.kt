package org.xhanka.biblesiswati.ui.main.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "INSTALLED_VERSIONS")
data class InstalledDb(
    @ColumnInfo(name = "VERSION_NAME") var versionName: String,
    @ColumnInfo(name = "VERSION_ENTRY_VALUE") var versionEntryValue: String,
    @ColumnInfo(name = "IS_INSTALLED") var isInstalled: Int,
    @ColumnInfo(name = "ID") @PrimaryKey(autoGenerate = true) var id: Int = 0
)