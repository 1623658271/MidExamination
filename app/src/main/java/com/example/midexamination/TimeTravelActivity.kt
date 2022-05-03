package com.example.midexamination

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midexamination.adapter.TimeTravelRVAdapter
import com.example.midexamination.model.StarData
import com.example.midexamination.viewmodel.StarViewModel

class TimeTravelActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTimeTravelRVAdapter: TimeTravelRVAdapter
    private lateinit var mStarList: MutableList<StarData>
    private lateinit var total:TextView
    private lateinit var sum:TextView
    private var mSumTime = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_travel)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        init()
    }
    private fun init(){
        total = findViewById(R.id.star_number)
        sum = findViewById(R.id.star_totalTime)
        mRecyclerView = findViewById(R.id.rv_time_travel)
        mStarList = StarViewModel.getStarLiveData(this).value!!
        mTimeTravelRVAdapter = TimeTravelRVAdapter(this,mStarList)

        mRecyclerView.adapter = mTimeTravelRVAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        for(i in mStarList){
            mSumTime += i.totalTime
        }
        sum.text = mSumTime.toString()
        total.text = mStarList.size.toString()
    }
}