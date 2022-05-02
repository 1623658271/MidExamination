package com.example.midexamination.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.midexamination.model.MyDatabase
import com.example.midexamination.model.StarData

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/1 12:28
 */
class StarViewModel( var context: Context): ViewModel() {

    companion object {
        private val TAG = "123"
        private var mutableLiveData:MutableLiveData<MutableList<StarData>>? = null
        fun getStarLiveData(context: Context): MutableLiveData<MutableList<StarData>> {
            if (mutableLiveData == null) {
                mutableLiveData = MutableLiveData(getStarListFromDatabase(context))
                Log.d(TAG, "getStarLiveData: ")
            }
            return mutableLiveData as MutableLiveData<MutableList<StarData>>
        }

        private fun getStarListFromDatabase(context: Context): MutableList<StarData> {
            var mMyDatabase: MyDatabase = MyDatabase.getDatabase(context)

            return mMyDatabase!!.getStarDao().getAllStarData()
        }
    }
}