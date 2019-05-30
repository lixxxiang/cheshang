package com.android.lixiang.cheshang.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.android.lixiang.cheshang.R
import kotlinx.android.synthetic.main.fragment_mission_list.*
import me.yokeyword.fragmentation.SupportFragment
import android.text.Spannable
import android.text.style.AbsoluteSizeSpan
import android.text.SpannableString
import com.android.lixiang.base.utils.view.DimenUtil
import com.blankj.utilcode.util.TimeUtils
import android.text.style.ForegroundColorSpan
import com.android.lixiang.cheshang.ui.adapter.MissionListAdapter
import com.android.lixiang.cheshang.ui.adapter.OCRListAdapter
import kotlinx.android.synthetic.main.fragment_ocr_list.*
import kotlinx.android.synthetic.main.fragment_ocr_scan.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class OCRScanFragment : SupportFragment(),View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            test -> {
                start(OCRResultFragment().newInstance())
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ocr_scan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        test.setOnClickListener(this)
    }

    fun newInstance(): OCRScanFragment {
        val args = Bundle()
        val fragment = OCRScanFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}
