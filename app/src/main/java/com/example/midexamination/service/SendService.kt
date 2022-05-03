package com.example.midexamination.service

import android.R
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.midexamination.MainActivity
import com.example.midexamination.viewmodel.StarViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/2 16:46
 */
class SendService(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    @SuppressLint("UnspecifiedImmutableFlag", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        var starList = StarViewModel.getStarLiveData(applicationContext).value!!
        var stringList:MutableList<String> = ArrayList()
        val dateFormat = SimpleDateFormat("yyyy年MM月dd日")
        val data= dateFormat.format(Date())
        for(i in starList){
            if(i.bigTime == data){
                stringList.add(i.name)
            }
        }
        if(stringList.size>0) {
            var notificationChannel: NotificationChannel?
            var notificationManager: NotificationManager? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel =
                    NotificationChannel("important", "Important", NotificationManager.IMPORTANCE_HIGH)
                notificationManager =
                    (applicationContext.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager)
                notificationManager.createNotificationChannel(notificationChannel)
            }
            val intent2 = Intent(applicationContext, MainActivity::class.java)
            val pi = PendingIntent.getActivity(applicationContext, 0, intent2, 0)
            val notification2: Notification = Notification.Builder(applicationContext, "important")
                .setContentTitle("星球点亮通知")
                .setContentText("有" + stringList.size + "个星球可以点亮啦")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_btn_speak_now)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build()
            notificationManager?.notify(2, notification2)
        }
        return Result.success()
    }

}