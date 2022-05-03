package com.example.midexamination.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.midexamination.R
import com.example.midexamination.SecondActivity
import com.example.midexamination.adapter.UniverseTwoAdapter
import com.example.midexamination.model.MyDatabase
import com.example.midexamination.model.StarData
import com.example.midexamination.viewmodel.StarViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/2 10:50
 */
class FragmentUniverse2(context:Context):Fragment() {
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var view0: View
    private var context0 = context
    private var p = 0
    private val TAG = "123"
    private lateinit var mImageView: ImageView
    private lateinit var bottomDialog: BottomSheetDialog
    private lateinit var mCheckButton: MaterialButton
    private lateinit var mRVAdapter: UniverseTwoAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mStarList: MutableList<StarData>
    private lateinit var mName: TextView
    private lateinit var mData: TextView
    private lateinit var mTime: TextView
    private lateinit var mRemark:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view0 = inflater.inflate(R.layout.activity_main_fragment_2_universe_fragment_2,container,false)
        return view0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        mRecyclerView = view0.findViewById(R.id.rv_universe_2)
        mStarList = StarViewModel.getStarLiveData(context0).value!!
        mSwipeRefreshLayout = view0.findViewById(R.id.universe2_refresh)

        mRVAdapter = UniverseTwoAdapter(context0,mStarList.filter { it.bigTime!= "已点亮"})

        bottomDialog = BottomSheetDialog(context0)
        bottomDialog.setContentView(R.layout.bottom_fragment2_universe_2)

        mCheckButton = bottomDialog.findViewById(R.id.mBtn_light)!!
        mName = bottomDialog.findViewById(R.id.iv_universe2_bottom_name)!!
        mData = bottomDialog.findViewById(R.id.tv_bottom_universe2_date)!!
        mRemark = bottomDialog.findViewById(R.id.tv_bottom_universe2_remark)!!
        mTime = bottomDialog.findViewById(R.id.tv_bottom_universe2_time)!!
        mImageView = bottomDialog.findViewById(R.id.iv_universe2_bottom_image)!!

        mRecyclerView.adapter = mRVAdapter
        mRecyclerView.layoutManager = GridLayoutManager(context0,2,RecyclerView.VERTICAL,false)
        set()

        mRVAdapter.setCreateListener(object :UniverseTwoAdapter.CreateListener{
            override fun createStar(view: View, position: Int) {
                startActivity()
            }
        })

        StarViewModel.getStarLiveData(context0).observe(viewLifecycleOwner){
            mRVAdapter.refreshData(it)
            mStarList = it.filter { it.bigTime!="已点亮" } as MutableList<StarData>
        }

        mCheckButton.setOnClickListener {
            if(mStarList[p].bigTime!="已点亮") {
                checkTime(mStarList[p].bigTime)
            }else{
                Toast.makeText(context0,"你已经点亮它了呀",Toast.LENGTH_SHORT).show()
            }
        }
        mSwipeRefreshLayout.setOnRefreshListener {
            mRVAdapter.refreshData(StarViewModel.getStarLiveData(context0).value!!)
            mRVAdapter.clearButtonList()
            mStarList = mStarList.filter { it.bigTime!="已点亮" }.toMutableList()
            set()
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun checkTime(bigTime: String) {
        val dateFormat = SimpleDateFormat("yyyy年MM月dd日")
        val data = dateFormat.format(Date())
        Log.d(TAG, "checkTime: "+data)
        if(data==bigTime){
            mCheckButton.text = "已点亮"
            Toast.makeText(context0,"点亮成功！",Toast.LENGTH_SHORT).show()
            mStarList[p].bigTime = "已点亮"
            MyDatabase.getDatabase(context0).getStarDao().update(mStarList[p])
            set()
        }else{
            Toast.makeText(context0,"未到指定时间哦,到时候会通知你的",Toast.LENGTH_SHORT).show()
        }
    }

    fun set(){
        mRVAdapter.setOnItemSelectedListener(object :UniverseTwoAdapter.OnItemSelectedListener{
            override fun onItemSelected(view: View, position: Int) {
                Log.d(TAG, "onItemSelected: $position\n"+mStarList.size)
                Glide.with(context0)
                    .load(mStarList[position].url)
                    .placeholder(R.drawable.ic_star)
                    .into(mImageView)
                mName.text = mStarList[position].name
                mTime.text = mStarList[position].totalTime.toString()
                mData.text = mStarList[position].bigTime
                mRemark.text = mStarList[position].remark
                bottomDialog.show()
                p = position
                if(mStarList[position].bigTime!="已点亮"){
                    mCheckButton.text = "点亮"
                }else{
                    mCheckButton.text = "已点亮"
                }
            }
        })
    }

    fun startActivity(){
        val intent = Intent(context0, SecondActivity::class.java)
        startActivity(intent)
    }
}