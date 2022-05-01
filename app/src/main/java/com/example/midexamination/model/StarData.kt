package com.example.midexamination.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/4/30 18:35
 */
@Entity(tableName = "Star")
data class StarData(var name:String,var url:String,var totalTime:Int,var bigTime:String,var remark:String) {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}