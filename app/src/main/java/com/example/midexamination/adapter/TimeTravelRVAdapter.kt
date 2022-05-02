package com.example.midexamination.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.midexamination.R
import com.example.midexamination.model.StarData
import com.example.midexamination.utils.ImageButtonSelectedUtil

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/2 20:18
 */
class TimeTravelRVAdapter(private val context: Context, var starList:MutableList<StarData>): RecyclerView.Adapter<TimeTravelRVAdapter.ViewHolder>() {
    private val TAG = "123"
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var mTime:TextView = itemView.findViewById(R.id.time_travel_star_time)
        var mName:TextView = itemView.findViewById(R.id.time_travel_star_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_time_travel,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mStarData = starList[position]
        holder.mName.text = mStarData.name
        holder.mTime.text = mStarData.totalTime.toString()
    }

    override fun getItemCount(): Int {
        return starList.size
    }
}