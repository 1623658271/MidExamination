package com.example.midexamination

import android.app.DatePickerDialog
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.util.StringUtil
import com.example.midexamination.adapter.CreateRVAdapter
import com.example.midexamination.adapter.TimerBottomRVAdapter
import com.example.midexamination.model.MyDatabase
import com.example.midexamination.model.StarData
import com.example.midexamination.viewmodel.StarViewModel
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.collections.ArrayList

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
    private lateinit var createBtn:MaterialButton
    private lateinit var edNewRemark:EditText
    private lateinit var tvNewTime:TextView
    private lateinit var edNewName:EditText
    private lateinit var starDataList: MutableList<String>
    private var p:Int = 0

    private lateinit var mRVAdapter: CreateRVAdapter
    private lateinit var mRecyclerView: RecyclerView

    private lateinit var dataPickerDialog:DatePickerDialog
    private lateinit var calendar:Calendar
    private lateinit var database: MyDatabase
    private lateinit var starViewModel: StarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        init()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun init(){
        edNewName = findViewById(R.id.et_second_newStarName)
        createBtn = findViewById(R.id.mBtn_create_newStar)
        edNewRemark = findViewById(R.id.et_create_remark)

        tvNewTime = findViewById(R.id.et_second_newStarTime)
        calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        monthYear = calendar[Calendar.MONTH]
        dayMonth = calendar[Calendar.DAY_OF_MONTH]

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dataPickerDialog = DatePickerDialog(this, { _, p1, p2, p3 ->
                var date:String
                if(p2+1<10 && p3>=10) {
                    date = p1.toString() + "年" + "0"+(p2 + 1) + "月" + p3 + "日"
                }else if(p3<10 && p2>=10){
                    date = p1.toString() + "年" + (p2 + 1) + "月" + "0" +p3 + "日"
                }else if(p2+1<10 && p3<10){
                    date = p1.toString() + "年" +"0"+ (p2 + 1) + "月" + "0" +p3 + "日"
                }else{
                    date = p1.toString() + "年" + (p2 + 1) + "月" +p3 + "日"
                }
                tvNewTime.text = date
            },year,monthYear,dayMonth)
        }
        tvNewTime.setOnClickListener {
            dataPickerDialog.show()
        }

        database = MyDatabase.getDatabase(this)
        starViewModel = StarViewModel(this)
        starDataList = ArrayList()
        starDataList.apply {
            add("https://img.51miz.com/Element/00/95/64/00/bbd8bd28_E956400_a973af88.png")
            add("https://img.zcool.cn/community/017a695be7faf6a801209252f414ff.jpg@1280w_1l_2o_100sh.jpg")
            add("https://tse1-mm.cn.bing.net/th/id/R-C.cf553b14756343140112e8f93ab7dc05?rik=330qPPzYc%2fFDxQ&riu=http%3a%2f%2fpic.sucaibar.com%2fpic%2f201308%2f09%2fe7be6a3e18.jpg&ehk=PQpJwUKE0rIbdtXbm9TvS28mcfrz4yu6suQr6sWQZb0%3d&risl=&pid=ImgRaw&r=0")
            add("https://bpic.588ku.com/element_origin_min_pic/19/10/01/41bda95914e5d1b8a4fa828b67acbd9c.jpg")
            add("https://tse1-mm.cn.bing.net/th/id/R-C.9dede9014e2f31012aa4a549032e5eb0?rik=gYoMCckKnQl3nA&riu=http%3a%2f%2fpic.616pic.com%2fys_bnew_img%2f00%2f56%2f10%2fJdyyLHRvIK.jpg&ehk=wl6xoqtALlGPO6PMl6kbT2WcaNz9eQK7KSiIZm23Z28%3d&risl=&pid=ImgRaw&r=0")
            add("https://tse1-mm.cn.bing.net/th/id/R-C.c33514961539296a0e24c162337270f1?rik=w3kMwEsEgdogDA&riu=http%3a%2f%2fpic.baike.soso.com%2fp%2f20090106%2f20090106120000-105450.jpg&ehk=GeWLW9QwZubYVbo9BvuxfBrEzKvhC7VxmUWxDg8eUNY%3d&risl=&pid=ImgRaw&r=0")
            add("https://tse1-mm.cn.bing.net/th/id/R-C.e7dc2af1173a313682b4bdc4e219b718?rik=IofVP21c0J98Pw&riu=http%3a%2f%2fimg1.mydrivers.com%2fimg%2f20180222%2fe0d55afbe9504b4a9bae7758c601446d.jpg&ehk=%2fzagtnbJYY2Af2wa2V6j0A8vwwJwd8nsOjLCzQDHtFo%3d&risl=&pid=ImgRaw&r=0")
            add("https://pic2.zhimg.com/v2-4e7308d674da0a1a55a8aaca86cea98c_720w.jpg?source=172ae18b")
            add("https://uploadfile.huiyi8.com/2018/af/07/4b/69/203274.jpg")
            add("https://www.jsdaima.com/kindeditor/attached/image/20181030/20181030214123_82602.jpg")
        }

        mRVAdapter = CreateRVAdapter(this,starDataList)
        mRecyclerView = findViewById(R.id.rv_second_newStarImage)
        mRecyclerView.adapter = mRVAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        mRVAdapter.setOnItemSelectedListener(object :CreateRVAdapter.OnItemSelectedListener{
            override fun onItemSelected(view: View, position: Int) {
                mRVAdapter.select(position)
                p = position
            }
        })

        createBtn.setOnClickListener {
            if(tvNewTime.text!="xx年xx月xx日" && !TextUtils.isEmpty(tvNewTime.text) && !TextUtils.isEmpty(edNewName.text)){
                database.getStarDao().insert(StarData(edNewName.text.toString(),
                starDataList[p],0,tvNewTime.text.toString(),edNewRemark.text.toString()))

                StarViewModel.getStarLiveData(this).value = (database.getStarDao().getAllStarData())
                Toast.makeText(this,"创建成功!",Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"请输入正确！",Toast.LENGTH_SHORT).show()
            }
        }
    }
}