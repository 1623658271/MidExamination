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
class StarViewModel(private var context: Context): ViewModel() {
    private val TAG = "123"
    private var mutableLiveData:MutableLiveData<MutableList<StarData>>? = null
    private var mMyDatabase:MyDatabase? = null

    fun getStarLiveData(): MutableLiveData<MutableList<StarData>> {
        if(mutableLiveData==null){
            mutableLiveData = MutableLiveData(getStarListFromDatabase())
        }
        return mutableLiveData as MutableLiveData<MutableList<StarData>>
    }

    private fun getStarListFromDatabase(): MutableList<StarData> {
        mMyDatabase = MyDatabase.getDatabase(context)
        Log.d(TAG, "getStarListFromDatabase: "+mMyDatabase+"\n"+mMyDatabase!!.getStarDao().getAllStarData().size)
        return mMyDatabase!!.getStarDao().getAllStarData()
    }
}