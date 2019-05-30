package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.cheshang.R
import me.yokeyword.fragmentation.SupportFragment
import android.support.v7.app.AppCompatActivity
import com.android.lixiang.cheshang.R.id.mChangePhoneModel
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.ui.adapter.ChooseReasonAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_cannot_login.*
import kotlinx.android.synthetic.main.fragment_choose_reason.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class ChooseReasonFragment : SupportFragment(), View.OnClickListener {
    private var mTitleList: MutableList<String>? = mutableListOf("更换手机型号", "更换手机号码")
    private var getUserByHrAccountBean: GetUserByHrAccountBean? = null

    override fun onClick(v: View?) {
        when (v) {
//            mChangePhoneModel -> {
//                start(ChangePhoneModelFragment().newInstance())
//
//            }
//
//            mChangePhoneNumber -> {
//                start(ChangePhoneNumberFragment().newInstance())
//            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_reason, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        mChooseReasonToolbar.title = "选择原因"
        (activity as AppCompatActivity).setSupportActionBar(mChooseReasonToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mChooseReasonToolbar.setNavigationOnClickListener {
            pop()
        }

        getUserByHrAccountBean = arguments!!.getSerializable("DATA") as GetUserByHrAccountBean

        mNameTV.text = getUserByHrAccountBean!!.data.name
        mHRTV.text = getUserByHrAccountBean!!.data.hrAccount
        mTelTV.text = getUserByHrAccountBean!!.data.tel
        if (getUserByHrAccountBean!!.data.deviceId == null) {
            mIMEITV.text = "-"
        } else
            mIMEITV.text = getUserByHrAccountBean!!.data.deviceId

        mChooseReasonLV.adapter = ChooseReasonAdapter(mTitleList, activity)
        mChooseReasonLV.setOnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {
                    val fragment = ChangePhoneModelFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putSerializable("DATA", getUserByHrAccountBean)
                    fragment.arguments = bundle
                    start(fragment)
                }
                1 -> {
                    val fragment = ChangePhoneNumberFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putSerializable("DATA", getUserByHrAccountBean)
                    fragment.arguments = bundle
                    start(fragment)
                }
            }
        }
//        mChangePhoneModel.setOnClickListener(this)
//        mChangePhoneNumber.setOnClickListener(this)

    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)

    }

    fun newInstance(): ChooseReasonFragment {
        val args = Bundle()
        val fragment = ChooseReasonFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}
