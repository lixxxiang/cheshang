package com.android.lixiang.cheshang.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.ui.adapter.CarStoreAdapter
import com.blankj.utilcode.util.TimeUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_car_store.*
import kotlinx.android.synthetic.main.fragment_info_report.*
import kotlinx.android.synthetic.main.fragment_mission_calendar.*
import kotlinx.android.synthetic.main.fragment_mission_list.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class MissionCalendarFragment : SupportFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mission_calendar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mMissionCalendarToolbar.title = "任务日历"

        (activity as AppCompatActivity).setSupportActionBar(mMissionCalendarToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mMissionCalendarToolbar.setNavigationOnClickListener {
            pop()
        }
        val dateOriginal = TimeUtils.getNowString().split(" ")[0]
        val date = dateOriginal.substring(5, dateOriginal.length)
        Logger.d(date.toCharArray()[0].toString())
        if (date.toCharArray()[0].toString() == "0") {
            mCalendarDateTV.text = date.substring(1, date.length).replace("-", "月") + "日"
        } else
            mCalendarDateTV.text = date.replace("-", "月") + "日"

        val string = "你有 32个 续保客户需要跟踪"
        val spanString2 = SpannableString(string)
        val span2 = ForegroundColorSpan(Color.parseColor("#F73E00"))
        spanString2.setSpan(span2, string.indexOf("有") + 1, string.indexOf("个") + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        mCalendarFollowTV.text = spanString2


        Logger.d(date)
        mCalendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val month2 = month + 1
            mCalendarDateTV.text = "" + month2 + "月" + dayOfMonth + "日"
        }
    }


    fun newInstance(): MissionCalendarFragment {
        val args = Bundle()
        val fragment = MissionCalendarFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}
