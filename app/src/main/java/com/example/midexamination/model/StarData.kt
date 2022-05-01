package com.example.midexamination.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/4/30 18:35
 */
@Entity(tableName = "Star")
data class StarData(
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var name:String,
    @ColumnInfo(name = "url", typeAffinity = ColumnInfo.TEXT)
    var url:String,
    @ColumnInfo(name = "totalTime", typeAffinity = ColumnInfo.INTEGER)
    var totalTime:Int,
    @ColumnInfo(name = "bigTime", typeAffinity = ColumnInfo.TEXT)
    var bigTime:String,
    @ColumnInfo(name = "remark", typeAffinity = ColumnInfo.TEXT)
    var remark:String) {

    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}