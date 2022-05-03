package com.example.midexamination.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
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
 * date : 2022/5/2 13:13
 */
class UniverseTwoAdapter(private val context: Context, var starList:List<StarData>): RecyclerView.Adapter<UniverseTwoAdapter.ViewHolder>() {
    private var mOnItemSelectedListener:OnItemSelectedListener? = null
    private var mCreateListener:CreateListener? = null
    private var buttonList:MutableList<ImageButton> = ArrayList()
    private var textViewList:MutableList<TextView> = ArrayList()
    private var imageButtonSelectedUtil = ImageButtonSelectedUtil()
    private val TAG = "123"
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var mImage: ImageButton = itemView.findViewById(R.id.ib_rv_first_bottom_image)
        var mName:TextView = itemView.findViewById(R.id.tv_rv_first_bottom_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_bottom_fragment1_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position!=starList.size) {
            Log.d(TAG, "onBindViewHolder: "+position+" "+buttonList.size )
            var starData = starList[position]
            if(buttonList.size<starList.size && !buttonList.contains(holder.mImage)) {
                buttonList.add(holder.mImage)
                textViewList.add(holder.mName)
            }
            Glide.with(context)
                .load(starData.url)
                .placeholder(R.drawable.ic_star)
                .into(holder.mImage)
            holder.mName.text = starData.name
            if (mOnItemSelectedListener != null) {
                holder.mImage.setOnClickListener {
                    cancelAll()
                    holder.mImage.isSelected = true
                    val position = holder.layoutPosition
                    mOnItemSelectedListener!!.onItemSelected(holder.itemView, position)
                }
            }
        }else{
            holder.mName.text = "创建星球"
            holder.mImage.setImageResource(R.drawable.ic_create_star)
            if(mCreateListener!=null){
                cancelAll()
                holder.mImage.setOnClickListener {
                    val position = holder.layoutPosition
                    mCreateListener!!.createStar(holder.itemView,position)
                }
            }
        }
    }
    fun cancelAll(){
        imageButtonSelectedUtil.cancel(buttonList,textViewList)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newData: List<StarData>) {
        this.starList = newData.filter { it.bigTime!="已点亮" }
        this.notifyDataSetChanged()
    }

    fun clearButtonList(){
        buttonList.clear()
    }

    fun select(){
        imageButtonSelectedUtil.selectButton(buttonList,Color.BLUE,textViewList)
    }

    fun select(position: Int){
        imageButtonSelectedUtil.selectButton(buttonList,Color.BLUE, textViewList,position)
    }

    fun isSelectedOne():Boolean{
        return imageButtonSelectedUtil.isOne(buttonList)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: "+starList.size+1)
        return starList.filter { it.bigTime!="已点亮" }.size+1
    }

    interface OnItemSelectedListener {
        fun onItemSelected(view: View, position: Int)
    }

    interface CreateListener {
        fun createStar(view:View,position: Int)
    }
    fun setCreateListener(createListener: CreateListener){
        this.mCreateListener = createListener
    }

    fun setOnItemSelectedListener(onItemSelectedListener: OnItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener
    }
}