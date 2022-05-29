package org.xhanka.biblesiswati.ui.downloader.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.room.withTransaction
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import org.xhanka.biblesiswati.MainActivity
import org.xhanka.biblesiswati.R
import org.xhanka.biblesiswati.common.DATA
import org.xhanka.biblesiswati.common.installNewVersion
import org.xhanka.biblesiswati.ui.downloader.model.ImportDb
import org.xhanka.biblesiswati.ui.main.room.BibleDataBase
import javax.inject.Inject

@AndroidEntryPoint
class ImportVersionService : Service() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var isServiceStarted = false

    private lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var bibleDataBase: BibleDataBase

    companion object {
        const val NOTIFICATION_ID = 16
        const val NOTIFICATION_CHANNEL_ID = "com.xhanka.biblesiswati.channel"

        const val ACTION_START_DOWNLOAD = "start_downloading"
        const val ACTION_SEND_PROGRESS = "send_progress"
        const val ACTION_DONE_DOWNLOAD = "don_downloading"
        const val PROGRESS_KEY = "progress"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null && intent.action == ACTION_START_DOWNLOAD && DATA.JSON.hasData()) {
            startService()
            installNewVersion()
        } else stopService()

        return START_NOT_STICKY
    }

    private fun installNewVersion() {
        isServiceStarted = true

        val gson = Gson()
        val json = gson.fromJson(DATA.JSON.jsonText, ImportDb::class.java)

        val totalCount = json.verses.size
        val importVerses = json.verses
        val dbName = json.dbName

        val bibleDao = bibleDataBase.bibleDao()
        val progressIntent = Intent(ACTION_SEND_PROGRESS)
        val doneIntent = Intent(ACTION_DONE_DOWNLOAD)

        scope.launch {
            coroutineScope {
                bibleDataBase.withTransaction {
                    dbName.installNewVersion(bibleDao, importVerses) {
                        updateNotification(it, totalCount, createNotification())
                        val triple = Triple(totalCount, it, dbName)
                        progressIntent.putExtra(PROGRESS_KEY, triple)
                        sendBroadcast(progressIntent)
                    }
                    sendBroadcast(doneIntent)
                }
                stopService()
            }
        }
    }

    private fun updateNotification(
        progress: Int,
        totalMax: Int,
        notificationBuilder: NotificationCompat.Builder
    ) {
        notificationBuilder.setProgress(totalMax, progress, false)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show()
    }

    private fun startService() {
        val notification = createNotification()
        notification.setProgress(100, 0, false)
        startForeground(NOTIFICATION_ID, createNotification().build())
    }

    private fun stopService() {
        stopForeground(false)
        stopSelf()
        isServiceStarted = false
    }

    private fun createNotification(): NotificationCompat.Builder {
        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Download additional bible versions",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Download additional bible versions channel."
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(false)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = Intent(this, MainActivity::class.java)
            .let { notificationIntent ->
                notificationIntent.putExtra("DOWNLOADED_VERSION", true)
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setVibrate(null)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle("Importing new version.")
            .setSubText("Installing database")
            .setContentText("Importing.")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setOnlyAlertOnce(true)
            .setStyle(NotificationCompat.InboxStyle().addLine("Installing new version."))
    }
}