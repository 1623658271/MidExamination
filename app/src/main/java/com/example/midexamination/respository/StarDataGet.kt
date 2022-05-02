package com.example.midexamination.respository

import android.content.Context
import com.example.midexamination.model.StarData
import com.example.midexamination.viewmodel.StarViewModel

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/2 09:28
 */
class StarDataGet {
    fun getStarData(context: Context):MutableList<StarData>{
        return StarViewModel.getStarLiveData(context).value!!
    }
}