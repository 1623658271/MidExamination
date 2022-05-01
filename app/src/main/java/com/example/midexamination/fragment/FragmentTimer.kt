package com.example.midexamination.fragment

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.midexamination.R
import com.example.midexamination.SecondActivity
import com.example.midexamination.adapter.TimerBottomRVAdapter
import com.example.midexamination.model.StarData
import com.example.midexamination.view.FragmentTimerViewChanges
import com.example.midexamination.viewmodel.StarViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/4/30 16:16
 */
class FragmentTimer(context: Context): Fragment(),FragmentTimerViewChanges,LifecycleOwner{
    private lateinit var view0:View
    private lateinit var chronometer: Chronometer
    private var p:Int = 0
    private var time = 0
    private val context0:Context = context
    private lateinit var mImageMain:ImageView
    private lateinit var mImageView:ImageView
    private lateinit var bottomDialog: BottomSheetDialog
    private lateinit var mCheckButton: MaterialButton
    private lateinit var mRVAdapter: TimerBottomRVAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mStarViewModel: StarViewModel
    private lateinit var mStarList: MutableList<StarData>
    private lateinit var mName:TextView
    private lateinit var mData:TextView
    private lateinit var mTime:TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editer: SharedPreferences.Editor
    private val TAG = "123"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view0 = inflater.inflate(R.layout.activity_main_fragment_1_timer, container, false)
        return view0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        editer = context0.getSharedPreferences("time",Context.MODE_PRIVATE).edit()
        sharedPreferences = context0.getSharedPreferences("time",Context.MODE_PRIVATE)
        chronometer = view0.findViewById(R.id.chronometer)
        mImageMain = view0.findViewById(R.id.iv_main)
        bottomDialog = BottomSheetDialog(context0)
        bottomDialog.setContentView(R.layout.bottom_fragment1_timer)
        mCheckButton = bottomDialog.findViewById(R.id.btn_first_bottom_change)!!
        mRecyclerView = bottomDialog.findViewById(R.id.rv_bottom_first)!!
        mName = bottomDialog.findViewById(R.id.tv_first_bottom_name)!!
        mData = bottomDialog.findViewById(R.id.tv_first_bottom_date)!!
        mTime = bottomDialog.findViewById(R.id.tv_first_bottom_time)!!

        mStarViewModel = StarViewModel(context0)
        mStarList = mStarViewModel.getStarLiveData().value!!
        Log.d(TAG, "init: "+mStarList.size)
        mRVAdapter = TimerBottomRVAdapter(context0,mStarList)
        mRVAdapter.apply {
            setOnItemSelectedListener(object: TimerBottomRVAdapter.OnItemSelectedListener{
                override fun onItemSelected(view: View, position: Int) {
                    mRVAdapter.select()
                    Log.d(TAG, "onItemSelected: ")
                    mName.text = mStarList[position].name
                    mData.text = mStarList[position].bigTime
                    mTime.text = mStarList[position].totalTime.toString()
                    p=position
                }
            })

            setCreateListener(object :TimerBottomRVAdapter.CreateListener{
                override fun createStar(view: View, position: Int) {
                    startActivity()
                }
            })
        }

        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(context0,LinearLayoutManager.HORIZONTAL,false)
            adapter = mRVAdapter
        }

        mImageView = view0.findViewById(R.id.iv_main)
        mImageView.setOnClickListener {
            showFragmentTimerBottom()
        }

        mCheckButton.setOnClickListener {
            Glide.with(this)
                .load(mStarList[p].url)
                .placeholder(R.drawable.ic_star)
                .into(mImageMain)
            chronometer.base = mStarList[p].totalTime.toLong()
            chronometer.start()
        }
    }


    /**
     * Show fragment timer bottom
     * 弹出星球选择界面
     */
    override fun showFragmentTimerBottom() {
        bottomDialog.show()
    }

    /**
     * Start timer
     * 开始计时
     */
    override fun startTimer() {
        chronometer.start()
    }

    fun startActivity(){
        val intent = Intent(context0,SecondActivity::class.java)
        startActivity(intent)
    }
}