package com.example.midexamination.model

import androidx.lifecycle.MutableLiveData
import androidx.room.*

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/4/30 18:44
 */
@Dao
interface StarDataDao {
    @Insert
    fun insert(starData: StarData):Long

    @Update
    fun update(starData: StarData)

    @Delete
    fun delete(starData: StarData)

    @Query("SELECT * FROM Star")
    fun getAllStarData():List<StarData>

    @Query("SELECT * FROM Star where name = :name")
    fun getStarDataByName(name:String):StarData

    @Query("DELETE FROM Star where name = :name1")
    fun deleteStarDataByName(name1:String)

}