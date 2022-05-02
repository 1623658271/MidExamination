package com.example.midexamination.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
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
 * date : 2022/5/2 11:02
 */
class UniverseOneAdapter(private val context: Context, private var starList:MutableList<StarData>): RecyclerView.Adapter<UniverseOneAdapter.ViewHolder>() {
    private var mOnItemSelectedListener:OnItemSelectedListener? = null
    private var buttonList:MutableList<ImageButton> = ArrayList()
    private var textViewList:MutableList<TextView> = ArrayList()
    private var imageButtonSelectedUtil = ImageButtonSelectedUtil()
    private var width = 600
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var mImage: ImageButton = itemView.findViewById(R.id.ib_universe1)
        var mName: TextView = itemView.findViewById(R.id.tv_universe1)
        var ll:LinearLayout = itemView.findViewById(R.id.ll_universe_1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_fragment2_universe_1_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var starData = starList[position]
        if(buttonList.size<starList.size) {
            buttonList.add(holder.mImage)
            textViewList.add(holder.mName)
        }
        Glide.with(context)
            .load(starData.url)
            .placeholder(R.drawable.ic_star)
            .into(holder.mImage)
        if(starData.bigTime=="已点亮"){
            holder.mName.text = "已点亮"
        }else{
            holder.mName.text = "未点亮"
        }
        if (mOnItemSelectedListener != null) {
            holder.mImage.setOnClickListener {
                cancelAll()
                holder.mImage.isSelected = true
                val position = holder.layoutPosition
                mOnItemSelectedListener!!.onItemSelected(holder.itemView, position)
            }
        }

        holder.ll.translationX = (0..width).random().toFloat()
    }
    fun cancelAll(){
        imageButtonSelectedUtil.cancel(buttonList,textViewList)
    }

    fun select(position: Int){
        imageButtonSelectedUtil.selectButton(buttonList, Color.BLUE,textViewList,position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newData: List<StarData>) {
        this.starList = newData.toMutableList()
        this.notifyDataSetChanged()
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