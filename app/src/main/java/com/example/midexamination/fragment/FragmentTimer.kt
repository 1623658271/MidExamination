package com.example.midexamination.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.midexamination.R
import com.example.midexamination.SecondActivity
import com.example.midexamination.adapter.TimerBottomRVAdapter
import com.example.midexamination.model.MyDatabase
import com.example.midexamination.model.StarData
import com.example.midexamination.view.FragmentTimerViewChanges
import com.example.midexamination.viewmodel.StarViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/4/30 16:16
 */
class FragmentTimer(context: Context): Fragment(),FragmentTimerViewChanges{
    private lateinit var view0:View
    private lateinit var chronometer: Chronometer
    private var p:Int = 0
    private var now = 0
    private var time = 0
    private val context0:Context = context
    private lateinit var mImageMain:ImageView
    private lateinit var mImageView:ImageView
    private lateinit var bottomDialog: BottomSheetDialog
    private lateinit var mCheckButton: MaterialButton
    private lateinit var mRVAdapter: TimerBottomRVAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mStarList: MutableList<StarData>
    private lateinit var mName:TextView
    private lateinit var mData:TextView
    private lateinit var mTime:TextView
    private lateinit var mChip:Chip
    private lateinit var dateFormat:SimpleDateFormat
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
        editer = context0.getSharedPreferences("data",Context.MODE_PRIVATE).edit()
        sharedPreferences = context0.getSharedPreferences("data", Context.MODE_PRIVATE)

        chronometer = view0.findViewById(R.id.chronometer)
        chronometer.format = "%s"
        chronometer.base = SystemClock.elapsedRealtime()-sharedPreferences.getLong("time",0)
        mImageMain = view0.findViewById(R.id.iv_main)
        mChip = view0.findViewById(R.id.chip_main)
        bottomDialog = BottomSheetDialog(context0)
        bottomDialog.setContentView(R.layout.bottom_fragment1_timer)
        mCheckButton = bottomDialog.findViewById(R.id.btn_first_bottom_change)!!
        mRecyclerView = bottomDialog.findViewById(R.id.rv_bottom_first)!!
        mName = bottomDialog.findViewById(R.id.tv_first_bottom_name)!!
        mData = bottomDialog.findViewById(R.id.tv_first_bottom_date)!!
        mTime = bottomDialog.findViewById(R.id.tv_first_bottom_time)!!

        mStarList = StarViewModel.getStarLiveData(context0).value!!
        mRVAdapter = TimerBottomRVAdapter(context0,mStarList)
        mRVAdapter.apply {
            setOnItemSelectedListener(object: TimerBottomRVAdapter.OnItemSelectedListener{
                override fun onItemSelected(view: View, position: Int) {
                    mRVAdapter.select(position)
                    Log.d(TAG, "onItemSelected: ")
                    mName.text = mStarList[position].name
                    mData.text = mStarList[position].bigTime
                    mTime.text = mStarList[position].totalTime.toString()+"秒"
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
            if(!TextUtils.isEmpty(mName.text)) {
                Glide.with(this)
                    .load(mStarList[p].url)
                    .placeholder(R.drawable.ic_star)
                    .into(mImageMain)
                chronometer.format = "%s"
                chronometer.base = (SystemClock.elapsedRealtime()- 1000*mStarList[p].totalTime).toLong()
                startTimer()
                mChip.text = mStarList[p].name
                Toast.makeText(context0, "切换成功！", Toast.LENGTH_SHORT).show()
                now = p
                bottomDialog.dismiss()
            }else{
                Toast.makeText(context0, "请选择星球", Toast.LENGTH_SHORT).show()
            }
        }
        StarViewModel.getStarLiveData(context0).observe(viewLifecycleOwner){
            mRVAdapter.refreshData(it)
            if(!mStarList.contains(it.last())){
                mStarList.add(it.last())
            }
            Log.d(TAG, "refreshLiveData ")
        }
        chronometer.setOnChronometerTickListener {
            if(bottomDialog.isShowing && now == p){
                mTime.text =((SystemClock.elapsedRealtime()-it.base)/1000).toString()+"秒"
                saveTime(mStarList[now])
            }else if(now==p){
                saveTime(mStarList[now])
            }

        }
        p = sharedPreferences.getInt("now",0)
        now = p
        Glide.with(context0)
            .load(sharedPreferences.getString("url",mStarList[now].url))
            .placeholder(R.drawable.ic_star)
            .into(mImageMain)
        mChip.text = sharedPreferences.getString("name",mStarList[now].name)
        mName.text = sharedPreferences.getString("name",mStarList[now].name)
        mData.text = mStarList[now].bigTime
        mImageView.setOnLongClickListener {
            true
        }
        startTimer()
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
        Log.d(TAG, "startTimer: "+chronometer.base+"\n"+
        chronometer.drawingTime +"\n"+chronometer.format+
                SystemClock.elapsedRealtime())
    }

    fun startActivity(){
        val intent = Intent(context0,SecondActivity::class.java)
        startActivity(intent)
    }

    fun saveTime(starData: StarData){
        var time = SystemClock.elapsedRealtime() - chronometer.base
        starData.totalTime = (time/1000).toInt()
        MyDatabase.getDatabase(context0).getStarDao().update(starData)

        Log.d(TAG, "saveTime: $time")
    }

    override fun onDestroy() {
        editer.apply {
            putString("name",mStarList[now].name)
            putString("url",mStarList[now].url)
            putLong("time",SystemClock.elapsedRealtime()-chronometer.base)
            putInt("now",now)
            apply()
        }
        saveTime(mStarList[now])
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }
}