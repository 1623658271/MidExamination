package com.example.midexamination.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/4/30 20:37
 */
@Database(entities = [StarData::class], version = 1)
abstract class MyDatabase : RoomDatabase(){
    abstract fun getStarDao():StarDataDao

    companion object{

        private var instance:MyDatabase? = null

        fun getDatabase(context: Context):MyDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context,MyDatabase::class.java,"star.db")
                    .allowMainThreadQueries()
                    .createFromAsset("stars.db")
                    .build()
            }
            return instance as MyDatabase
        }
    }

    fun getDataBase() : MyDatabase? = instance
}