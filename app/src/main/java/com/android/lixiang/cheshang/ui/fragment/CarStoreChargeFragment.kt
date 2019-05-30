package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.data.bean.GetUserByHrAccountBean
import com.android.lixiang.cheshang.ui.adapter.CarStoreChargeAdapter
import kotlinx.android.synthetic.main.fragment_car_store_charge.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class CarStoreChargeFragment : SupportFragment() {
    private var mTitleList: MutableList<String>? = mutableListOf()
    private var mDetailList: MutableList<String>? = mutableListOf()
    private var mGetUserByHrAccountBeanDataBean: GetUserByHrAccountBean.DataBean ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_store_charge, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mCarStoreChargeToolbar.title = "负责车商店"

        (activity as AppCompatActivity).setSupportActionBar(mCarStoreChargeToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mCarStoreChargeToolbar.setNavigationOnClickListener {
            pop()
        }

        mGetUserByHrAccountBeanDataBean = arguments!!.getSerializable("SHOP") as GetUserByHrAccountBean.DataBean
        for(i in 0 until mGetUserByHrAccountBeanDataBean!!.shopList.size){
            mTitleList!!.add(mGetUserByHrAccountBeanDataBean!!.shopList[i].shopName)
            mDetailList!!.add(mGetUserByHrAccountBeanDataBean!!.shopList[i].address.toString())
        }

        val adapter = CarStoreChargeAdapter(mTitleList, mDetailList, context)
        mCarStoreChargeLV.adapter = adapter
        mCarStoreChargeLV.choiceMode = AbsListView.CHOICE_MODE_SINGLE
//        mCarStoreChargeLV.setOnItemClickListener { adapterView, view, i, l ->
//            mCarStoreChargeLV.visibility = View.VISIBLE
//            adapter.notifyDataSetInvalidated()
//        }
    }

    fun newInstance(): CarStoreChargeFragment {
        val args = Bundle()
        val fragment = CarStoreChargeFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}
