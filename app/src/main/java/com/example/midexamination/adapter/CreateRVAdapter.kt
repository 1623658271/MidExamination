package com.example.midexamination.adapter

import android.content.Context
import android.graphics.Color
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
 * date : 2022/5/1 18:49
 */
class CreateRVAdapter(private val context: Context, private var starList:MutableList<String>): RecyclerView.Adapter<CreateRVAdapter.ViewHolder>() {
    private var mOnItemSelectedListener:OnItemSelectedListener? = null
    private var buttonList:MutableList<ImageButton> = ArrayList()
    private var textViewList:MutableList<TextView> = ArrayList()
    private var imageButtonSelectedUtil = ImageButtonSelectedUtil()
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var mImage: ImageButton = itemView.findViewById(R.id.iv_rv_second_image_select)
        var mName: TextView = itemView.findViewById(R.id.tv_second_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_second_appearance_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var starData = starList[position]
        if(buttonList.size<starList.size) {
            buttonList.add(holder.mImage)
            textViewList.add(holder.mName)
        }
        Glide.with(context)
            .load(starData)
            .placeholder(R.drawable.ic_star)
            .into(holder.mImage)
        if (mOnItemSelectedListener != null) {
            holder.mImage.setOnClickListener {
                cancelAll()
                holder.mImage.isSelected = true
                val position = holder.layoutPosition
                mOnItemSelectedListener!!.onItemSelected(holder.itemView, position)
            }
        }

    }
    fun cancelAll(){
        imageButtonSelectedUtil.cancel(buttonList,textViewList)
    }

    fun select(position: Int){
        imageButtonSelectedUtil.selectButton(buttonList, Color.BLUE,textViewList,position)
    }

    fun isSelectedOne():Boolean{
        return imageButtonSelectedUtil.isOne(buttonList)
    }

    override fun getItemCount(): Int {
        return starList.size
    }

    interface OnItemSelectedListener {
        fun onItemSelected(view: View, position: Int)
    }

    fun setOnItemSelectedListener(onItemSelectedListener: OnItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener
    }
}