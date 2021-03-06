package com.example.midexamination.service

import android.R
import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.midexamination.MainActivity
import com.example.midexamination.viewmodel.StarViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class CountService : Service() {
    private var context = this
    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        var notificationChannel: NotificationChannel?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel("important", "Important", NotificationManager.IMPORTANCE_LOW)
            val notificationManager =
                (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val intent = Intent(this, MainActivity::class.java)
        val pi:PendingIntent
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S){
            pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }else{
            pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }

        val notification1: Notification = Notification.Builder(this, "important")
            .setContentTitle("正在帮您照看宇宙")
            .setContentText("每隔一段时间查看是否有星球可点亮")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_btn_speak_now)
            .setContentIntent(pi)
            .build()
        startForeground(1, notification1)
        var sendWorkRequest = PeriodicWorkRequest.Builder(SendService::class.java,15,TimeUnit.MINUTES)
            .addTag("通知更新星球的work")
            .setInitialDelay(2,TimeUnit.SECONDS)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this).enqueue(sendWorkRequest)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
