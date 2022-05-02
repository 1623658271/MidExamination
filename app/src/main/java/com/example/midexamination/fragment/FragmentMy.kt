package com.example.midexamination.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.midexamination.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import de.hdodenhof.circleimageview.CircleImageView

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/1 14:20
 */
class FragmentMy(context:Context):Fragment() {
    private lateinit var view0:View
    private var context0 = context
    private lateinit var mImageView: CircleImageView
    private lateinit var mName:TextView
    private lateinit var mBottomSheetDialog: BottomSheetDialog
    private lateinit var mTimeTravel:TextView
    private lateinit var mHonor:TextView
    private lateinit var mEdit1:EditText
    private lateinit var mEdit2:EditText
    private lateinit var mEImage:CircleImageView
    private lateinit var mBtnOk:MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view0 = inflater.inflate(R.layout.activity_main_fragment_3_my,container,false)
        return view0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        mImageView = view0.findViewById(R.id.my_image)
        mImageView.setOnClickListener {
            Toast.makeText(context0,"想点这里换头像?不行=-=",Toast.LENGTH_SHORT).show()
        }

        mName = view0.findViewById(R.id.tv_my_name)
        mTimeTravel = view0.findViewById(R.id.tv_time_travel)
        mHonor = view0.findViewById(R.id.tv_time_honor)

        mBottomSheetDialog = BottomSheetDialog(context0)
        mBottomSheetDialog.setContentView(R.layout.bottom_fragment3_create)

        mEdit1 = mBottomSheetDialog.findViewById(R.id.edit_name)!!
        mEdit2 = mBottomSheetDialog.findViewById(R.id.edit_saying)!!
        mEImage = mBottomSheetDialog.findViewById(R.id.edit_image)!!
        mBtnOk = mBottomSheetDialog.findViewById(R.id.edit_btn_ok)!!
    }
    fun initMessage(){

    }
}