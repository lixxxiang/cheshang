package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.ui.adapter.RepeatSettingAdapter
import kotlinx.android.synthetic.main.fragment_repeat_setting.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class RepeatSettingFragment : SupportFragment(), View.OnClickListener {
    private var mTitleList: MutableList<String>? = mutableListOf("永不", "每天", "每周", "每两周", "每月", "每年")
    private var mIndex: Int? = -1
    override fun onClick(p0: View?) {
        when (p0) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repeat_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mRepeatSettingToolbar.title = "重复"

        (activity as AppCompatActivity).setSupportActionBar(mRepeatSettingToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mRepeatSettingToolbar.setNavigationOnClickListener {
            val bundle = Bundle()
            bundle.putString("REPEAT", "")
            bundle.putInt("REPEATINDEX", -1)
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
        }
        mDateTV.text = Utils().timeFormat()

        mIndex = arguments!!.getInt("INDEX")


        val adapter = RepeatSettingAdapter(mTitleList, context)
        mRepeatSettingListview.adapter = adapter
        mRepeatSettingListview.choiceMode = AbsListView.CHOICE_MODE_SINGLE
        mRepeatSettingListview.setOnItemClickListener { adapterView, view, i, l ->
            adapter.setSelectedItem(i)
            adapter.notifyDataSetInvalidated()
            val bundle = Bundle()
            bundle.putString("REPEAT", mTitleList!![i])
            bundle.putInt("REPEATINDEX", i)
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
        }

        if(mIndex != -1){
            adapter.setIndex(mIndex!!.toInt())
            adapter.notifyDataSetChanged()
        }
    }

    fun newInstance(): RepeatSettingFragment {
        val args = Bundle()
        val fragment = RepeatSettingFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}