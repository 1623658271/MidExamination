package com.example.midexamination.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.midexamination.fragment.FragmentMy

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/1 19:58
 */
class FragmentPagerAdapter : FragmentStateAdapter {
    private var fragments: List<Fragment>

    constructor(fragmentActivity: FragmentActivity, fragments: List<Fragment>) : super(
        fragmentActivity
    ) {
        this.fragments = fragments
    }

    constructor(fragment: Fragment, fragments: List<Fragment>) : super(
        fragment
    ) {
        this.fragments = fragments
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}