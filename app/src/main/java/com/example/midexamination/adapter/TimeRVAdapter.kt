package com.example.midexamination.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.midexamination.R
import com.example.midexamination.model.StarData

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/2 21:26
 */
class TimeRVAdapter(private val context: Context, var starList:MutableList<StarData>,var numberList:MutableList<Int>): RecyclerView.Adapter<TimeRVAdapter.ViewHolder>() {
    private val TAG = "123"
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var box: CheckBox = itemView.findViewById(R.id.item_cb_time)
        var purpose:TextView = itemView.findViewById(R.id.item_tv_number_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_number_time,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = numberList[position]
        var p = 0
        for(i in starList){
            p+=i.totalTime
        }
        holder.box.isChecked = p>=number*60
        holder.purpose.text = number.toString()
    }

    override fun getItemCount(): Int {
        return starList.size
    }
}