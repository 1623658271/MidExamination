package com.example.midexamination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midexamination.adapter.NumberRVAdapter
import com.example.midexamination.adapter.TimeRVAdapter
import com.example.midexamination.adapter.UniverseTwoAdapter
import com.example.midexamination.model.StarData
import com.example.midexamination.viewmodel.StarViewModel

class HonorActivity : AppCompatActivity() {
    private lateinit var numberRecyclerView: RecyclerView
    private lateinit var timeRecyclerView: RecyclerView
    private lateinit var numberAdapter:NumberRVAdapter
    private lateinit var timeAdapter: TimeRVAdapter
    private lateinit var mStarDataList:MutableList<StarData>
    private lateinit var numberList:MutableList<Int>
    private lateinit var timeList:MutableList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_honor)

        init()
    }

    private fun init(){
        mStarDataList = StarViewModel.getStarLiveData(this).value!!

        numberRecyclerView = findViewById(R.id.rv_honor1_number)
        timeRecyclerView = findViewById(R.id.rv_honor1_totalTime)

        numberList = listOf(2,4,6,8,10,12,14,16,18,20).toMutableList()
        timeList = listOf(5,10,15,20,25,30,35,40,45,50).toMutableList()

        numberAdapter = NumberRVAdapter(this,mStarDataList,numberList)
        timeAdapter = TimeRVAdapter(this,mStarDataList,timeList)

        numberRecyclerView.adapter = numberAdapter
        numberRecyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        timeRecyclerView.adapter = timeAdapter
        timeRecyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
    }
}