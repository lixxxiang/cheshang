package com.android.lixiang.cheshang.ui.fragment

import android.animation.Animator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.ui.adapter.MyTrackAdapter
import kotlinx.android.synthetic.main.fragment_car_store.*
import kotlinx.android.synthetic.main.fragment_info_report.*
import kotlinx.android.synthetic.main.fragment_my_track.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class MyTrackFragment : SupportFragment() {
    private var mTitleList: MutableList<String>? = mutableListOf("通立冠宝","通立冠宝","通立冠宝","通立冠宝")
    private var mDetailList: MutableList<String>? = mutableListOf("距离 0.1 km | 吉林省长春市净月开发区生态大街1214号","距离 0.1 km | 吉林省长春市净月开发区生态大街1214号","距离 0.1 km | 吉林省长春市净月开发区生态大街1214号","距离 0.1 km | 吉林省长春市净月开发区生态大街1214号")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_track, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mMyTrackToolbar.title = "我的足迹"

        (activity as AppCompatActivity).setSupportActionBar(mMyTrackToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mMyTrackToolbar.setNavigationOnClickListener {
            pop()
        }
        val adapter = MyTrackAdapter(mTitleList, mDetailList, context)
        mMyTrackLV.adapter = adapter
        mMyTrackLV.choiceMode = AbsListView.CHOICE_MODE_SINGLE
        mMyTrackLV.setOnItemClickListener { adapterView, view, i, l ->
            mMyTrackLV.visibility = View.VISIBLE
            adapter.setSelectedItem(i)
            adapter.notifyDataSetInvalidated()
        }
    }

    fun newInstance(): MyTrackFragment {
        val args = Bundle()
        val fragment = MyTrackFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}
