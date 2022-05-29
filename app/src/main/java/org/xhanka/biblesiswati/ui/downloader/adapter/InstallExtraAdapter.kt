package org.xhanka.biblesiswati.ui.downloader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.Utils.Companion.INSTALLED_DB_COMPARATOR
import org.xhanka.biblesiswati.ui.main.models.InstalledDb

class InstallExtraAdapter :
    ListAdapter<InstalledDb, InstallExtraAdapter.ImportDbVH>(INSTALLED_DB_COMPARATOR) {

    class ImportDbVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val versionNameTexView: TextView = itemView.findViewById(R.id.versionName)
        private val isInstalledChip: MaterialButton = itemView.findViewById(R.id.isInstalled)
        private val downloadVersionChip: MaterialButton =
            itemView.findViewById(R.id.downloadVersion)

        fun onBind(installedDb: InstalledDb) {
            versionNameTexView.text = installedDb.versionName

            isInstalledChip.isEnabled = (installedDb.isInstalled == 1)
            downloadVersionChip.isEnabled = installedDb.isInstalled != 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImportDbVH {
        return ImportDbVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout._install_db_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImportDbVH, position: Int) {
        holder.onBind(getItem(position))
    }
}