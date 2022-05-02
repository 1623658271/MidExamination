package com.example.midexamination.service

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
    private var context = context

    override fun doWork(): Result {

        return Result.success()
    }
}