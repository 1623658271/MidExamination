package com.example.midexamination.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.midexamination.R
import com.example.midexamination.adapter.CreateRVAdapter
import com.example.midexamination.adapter.UniverseOneAdapter
import com.example.midexamination.model.StarData
import com.example.midexamination.viewmodel.StarViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/2 10:30
 */
class FragmentUniverse1(context: Context):Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: UniverseOneAdapter
    private lateinit var view0:View
    private var context0 = context
    private lateinit var mBottomSheetDialog: BottomSheetDialog
    private lateinit var mName: TextView
    private lateinit var mDate: TextView
    private lateinit var mTime: TextView
    private lateinit var mRemark: TextView
    private lateinit var mStarDataList: MutableList<StarData>
    private lateinit var mPicture:ImageView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view0 = inflater.inflate(R.layout.activity_main_fragment_2_universe_fragment_1,container,false)
        return view0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        mSwipeRefreshLayout = view0.findViewById(R.id.refresh_universe1)
        mBottomSheetDialog = BottomSheetDialog(context0)
        mBottomSheetDialog.setContentView(R.layout.bottom_fragment2_universe_1)
        mName = mBottomSheetDialog.findViewById(R.id.tv_bottom_first_universe_name)!!
        mDate = mBottomSheetDialog.findViewById(R.id.tv_bottom_first_universe_date)!!
        mTime = mBottomSheetDialog.findViewById(R.id.tv_bottom_first_universe_time)!!
        mRemark = mBottomSheetDialog.findViewById(R.id.tv_bottom_first_universe_remark)!!
        mPicture = mBottomSheetDialog.findViewById(R.id.iv_bottom_first_universe_image)!!

        mStarDataList = StarViewModel.getStarLiveData(context0).value!!

        mAdapter = UniverseOneAdapter(context0,mStarDataList)
        mRecyclerView = view0.findViewById(R.id.rv_first_universe_1)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = GridLayoutManager(context0,1, RecyclerView.VERTICAL,false)

        mAdapter.setOnItemSelectedListener(object :UniverseOneAdapter.OnItemSelectedListener{
            override fun onItemSelected(view: View, position: Int) {

                mName.text = mStarDataList[position].name
                mDate.text = mStarDataList[position].bigTime
                mRemark.text = mStarDataList[position].remark
                Glide.with(context0)
                    .load(mStarDataList[position].url)
                    .placeholder(R.drawable.ic_star)
                    .into(mPicture)
                mTime.text = mStarDataList[position].totalTime.toString()+"秒"
                mBottomSheetDialog.show()
            }
        })
        StarViewModel.getStarLiveData(context0).observe(viewLifecycleOwner){
            mAdapter.refreshData(it)
        }
        mSwipeRefreshLayout.setOnRefreshListener {
            mAdapter.refreshData(StarViewModel.getStarLiveData(context0).value!!)
            mSwipeRefreshLayout.isRefreshing = false
        }
    }
}