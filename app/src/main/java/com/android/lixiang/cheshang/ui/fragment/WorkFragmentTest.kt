package com.android.lixiang.cheshang.ui.fragment

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jpush.android.api.JPushInterface
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.R.id.mBSV
import com.android.lixiang.cheshang.R.id.mWorkToolbar
import com.android.lixiang.cheshang.presenter.WorkPresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionByHrAccountBean
import com.android.lixiang.cheshang.presenter.data.bean.GetShopByHrAccountAndPositionBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerWorkFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.WorkModule
import com.android.lixiang.cheshang.presenter.view.WorkView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.NetworkChangeReceiver
import com.android.lixiang.cheshang.util.ToastUtil
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_work.*
import me.yokeyword.fragmentation.SupportFragment


class WorkFragmentTest : SupportFragment(), View.OnClickListener {

    override fun onClick(v: View?) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_test, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logger.d(mBSV.getChildAt(0).height)
        initViews()
    }

    private fun initViews() {
        mWorkToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mWorkToolbar)
        mWorkToolbar.setNavigationOnClickListener {
            pop()
        }
    }

    fun newInstance(): WorkFragmentTest {
        val args = Bundle()
        val fragment = WorkFragmentTest()
        fragment.arguments = args
        return fragment
    }
}
