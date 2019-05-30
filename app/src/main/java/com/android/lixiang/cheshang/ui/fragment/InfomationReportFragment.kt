package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.util.ToastUtil
import com.android.lixiang.cheshang.ui.adapter.InfomationReportAdapter
import kotlinx.android.synthetic.main.fragment_infomation_report.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class InfomationReportFragment : SupportFragment(), View.OnClickListener {
    private var mTitleList: MutableList<String>? = mutableListOf("车商店信息上报", "行驶证扫描")
    private var mDetailList: MutableList<String>? = mutableListOf("其他主体销售政策、店内活动信息上报", "行驶证信息上报")

    override fun onClick(v: View?) {
        when (v) {
//            mCarStoreInfoReportRL -> {
//                start(CarStoreInfoReportFragment().newInstance())
//            }
//            mCardSearchRL -> {
//                start(OCRListFragment().newInstance())
//
//            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_infomation_report, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        mInfomationReportLV.isEnabled = true
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mInfomationReportToolbar.title = "信息上报"


        (activity as AppCompatActivity).setSupportActionBar(mInfomationReportToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mInfomationReportToolbar.setNavigationOnClickListener {
            pop()
        }
        mInfomationReportLV.adapter = InfomationReportAdapter(mTitleList, mDetailList, activity)
        mInfomationReportLV.setOnItemClickListener { adapterView, view, position, id ->
            mInfomationReportLV.isEnabled = false
            when (position) {
                0 -> startForResult(CarStoreInfoReportFragment().newInstance(), 0x008)
                1 -> {
                    val fragment = OCRListFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putInt("INDEX", arguments!!.getInt("INDEX"))
                    fragment.arguments = bundle
                    start(fragment)
                }
//                    start(OCRListFragment().newInstance())
            }
        }
//        mCarStoreInfoReportRL.setOnClickListener(this)
//        mCardSearchRL.setOnClickListener(this)
    }

    fun newInstance(): InfomationReportFragment {
        val args = Bundle()
        val fragment = InfomationReportFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x008 && resultCode == ISupportFragment.RESULT_OK) {
            if (data!!.getString("TAG") == "SUCCESS") {
//                Snackbar.make(view!!, "信息上报成功", Snackbar.LENGTH_SHORT).show()
                ToastUtil().toast2(activity!!,"信息上报成功")
            }
        }
    }
}
