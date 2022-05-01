package com.example.midexamination

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/4/30 21:26
 */
class SecondActivity : AppCompatActivity() {
    private var dayMonth: Int = 0
    private var monthYear:Int = 0
    private var year:Int = 0
    private lateinit var tvNewTime:TextView
    private lateinit var dataPickerDialog:DatePickerDialog
    private lateinit var calendar:Calendar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        init()
    }

    private fun init(){
        tvNewTime = findViewById(R.id.et_second_newStarTime)
        calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        monthYear = calendar[Calendar.MONTH]
        dayMonth = calendar[Calendar.DAY_OF_MONTH]

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dataPickerDialog = DatePickerDialog(this, { _, p1, p2, p3 ->
                val date = p1.toString()+"年"+(p2+1)+"月"+p3+"日"
                tvNewTime.text = date
            },year,monthYear,dayMonth)
        }
        tvNewTime.setOnClickListener {
            dataPickerDialog.show()
        }
    }
}