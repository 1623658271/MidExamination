package com.example.midexamination

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.midexamination.adapter.FragmentPagerAdapter
import com.example.midexamination.fragment.FragmentMy
import com.example.midexamination.fragment.FragmentTimer
import com.example.midexamination.fragment.FragmentUniverse
import com.example.midexamination.service.CountService

class MainActivity : AppCompatActivity(),RadioGroup.OnCheckedChangeListener {
    private lateinit var radioGroup: RadioGroup
    private lateinit var vp2:ViewPager2
    private lateinit var vp2Adapter:FragmentPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        startMessage()
    }

    private fun startMessage() {
        startService(Intent(this,CountService::class.java))
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.RB_time_counter -> vp2.currentItem = 0
            R.id.RB_universe -> vp2.currentItem = 1
            R.id.RB_my ->vp2.currentItem = 2
        }
    }

    private fun init(){
        val list:List<Fragment> = listOf(FragmentTimer(this),FragmentUniverse(this),FragmentMy(this))
        radioGroup = findViewById(R.id.RG)
        radioGroup.setOnCheckedChangeListener(this)
        vp2 = findViewById(R.id.vp2_fragment_1)
        vp2Adapter = FragmentPagerAdapter(this,list)
        vp2.adapter = vp2Adapter
        vp2.currentItem = 0
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}