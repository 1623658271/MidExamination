package com.example.midexamination.utils

import android.graphics.Color
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/1 11:55
 */
class ImageButtonSelectedUtil {
    private val TAG = "123"
    fun selectButton(buttonList: MutableList<ImageButton>, color: Int,textViewList:MutableList<TextView>){
        var theFirst:Boolean = false
        for (i in 0 until buttonList.size){
            if(!theFirst){
                if(buttonList[i].isSelected)
                {
                    Log.d(TAG, "selectButton: "+buttonList[i].isSelected)
                    buttonList[i].isSelected = false
                    theFirst=true
                    textViewList[i].setTextColor(color)
                }
            }else{
                buttonList[i].isSelected = false
                textViewList[i].setTextColor(Color.BLACK)
            }
        }
    }
    fun selectButton(buttonList: MutableList<ImageButton>, color: Int,textViewList:MutableList<TextView>,position:Int){
        for (i in 0 until buttonList.size){
            Log.d(TAG, "selectButton: "+buttonList.size)
            if(i==position){
                buttonList[i].isSelected = true
                textViewList[i].setTextColor(color)
            }else{
                buttonList[i].isSelected = false
                textViewList[i].setTextColor(Color.BLACK)
            }
        }
    }
    fun cancel(buttonList: MutableList<ImageButton>,textViewList:MutableList<TextView>){
        for (button in buttonList){
            button.isSelected = false
        }
        for(text in textViewList){
            text.setTextColor(Color.BLACK)
        }
    }
    fun isOne(buttonList: MutableList<ImageButton>):Boolean{
        var flag:Boolean = false
        for(button in buttonList){
            if(button.isSelected){
                flag = true
                break
            }
        }
        return flag
    }
}