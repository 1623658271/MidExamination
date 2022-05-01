package com.example.midexamination

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import com.example.midexamination.fragment.FragmentMy
import com.example.midexamination.fragment.FragmentTimer
import com.example.midexamination.fragment.FragmentUniverse

class MainActivity : AppCompatActivity(),RadioGroup.OnCheckedChangeListener {
    private lateinit var radioGroup: RadioGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1){
            R.id.RB_time_counter -> supportFragmentManager.beginTransaction().replace(R.id.FL, FragmentTimer(this)).commit()
            R.id.RB_universe ->supportFragmentManager.beginTransaction().replace(R.id.FL, FragmentUniverse()).commit()
            R.id.RB_my ->supportFragmentManager.beginTransaction().replace(R.id.FL, FragmentMy()).commit()
        }
    }

    private fun init(){
        supportFragmentManager.beginTransaction().replace(R.id.FL, FragmentTimer(this)).commit()
        radioGroup = findViewById(R.id.RG)
        radioGroup.setOnCheckedChangeListener(this)
        startActivity(Intent(this,SecondActivity::class.java))
    }
}