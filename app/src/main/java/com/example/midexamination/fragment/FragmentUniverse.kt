package com.example.midexamination.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.midexamination.R
import com.example.midexamination.adapter.CreateRVAdapter
import com.example.midexamination.adapter.FragmentPagerAdapter
import com.example.midexamination.adapter.TimerBottomRVAdapter
import com.example.midexamination.model.StarData
import com.example.midexamination.viewmodel.StarViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.ChipGroup

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/1 14:20
 */
class FragmentUniverse(context: Context):Fragment(),ChipGroup.OnCheckedChangeListener{
    private lateinit var view0:View
    private var context0 = context
    private lateinit var vp2:ViewPager2
    private lateinit var mChipGroup:ChipGroup
    private lateinit var vp2Adapter: FragmentPagerAdapter
    private lateinit var fragmentList: MutableList<Fragment>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view0 = inflater.inflate(R.layout.activity_main_fragment_2_universe,container,false)
        return view0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        mChipGroup = view0.findViewById(R.id.CG)
        mChipGroup.setOnCheckedChangeListener(this)

        fragmentList = ArrayList()
        fragmentList.add(FragmentUniverse1(context0))
        fragmentList.add(FragmentUniverse2(context0))

        vp2 = view0.findViewById(R.id.vp2_fragment_universe)
        vp2Adapter = FragmentPagerAdapter(this,fragmentList)

        vp2.adapter = vp2Adapter
        vp2.currentItem = 0

    }

    override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {
        when(checkedId){
            R.id.cp_fragment2_universe->vp2.currentItem=0
            R.id.cp_fragment2_wait->vp2.currentItem=1
        }
    }
}