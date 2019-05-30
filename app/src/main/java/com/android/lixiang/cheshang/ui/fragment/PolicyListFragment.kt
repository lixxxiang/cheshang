package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.ui.fragment.BaseMvpFragment

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.PolicyListPresenter
import com.android.lixiang.cheshang.presenter.data.bean.PushDocumentBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerPushDocumentFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.PushDocumentModule
import com.android.lixiang.cheshang.presenter.view.PolicyListView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.util.ToastUtil
import com.android.lixiang.cheshang.ui.adapter.PolicyAdapter
import com.example.lixiang.testalertdialog.LoadingDialog2
import kotlinx.android.synthetic.main.fragment_policy_list.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class PolicyListFragment : BaseMvpFragment<PolicyListPresenter>(), PolicyListView {
    override fun returnPushDocumentError() {
        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
        progressBar.visibility = View.GONE
    }

    override fun injectComponent() {
        DaggerPushDocumentFragmentComponent.builder().fragmentComponent(fragmentComponent).pushDocumentModule(PushDocumentModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnPushDocument(pushDocumentBean: PushDocumentBean) {
        progressBar.visibility = View.GONE

        if (pushDocumentBean.message == "success") {

            for (i in 0 until pushDocumentBean.data.size) {
                mTitleList!!.add(pushDocumentBean.data[i].name)
                mPath!!.add(pushDocumentBean.data[i].filePath)
            }
            val adapter = PolicyAdapter(mTitleList, context)
            mPolicyLV.adapter = adapter
            mPolicyLV.setOnItemClickListener { adapterView, view, i, l ->
                val fragment = PolicyDetailFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("URL", mPath!![i])
                fragment.arguments = bundle
                start(fragment)
            }
        } else if (pushDocumentBean.status == 103) {
            mNotFoundRL.visibility = View.VISIBLE
            mPolicyLV.visibility = View.GONE

        }
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)


    }

    private var mTitleList: MutableList<String>? = mutableListOf()
    private var mPath: MutableList<String>? = mutableListOf()

    private var DEPTID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_policy_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mPolicyToolbar.title = "政策查看"
        (activity as AppCompatActivity).setSupportActionBar(mPolicyToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mPolicyToolbar.setNavigationOnClickListener {
            pop()
        }
        progressBar.visibility = View.VISIBLE
        DEPTID = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].deptId
        Handler().postDelayed({
            mPresenter.pushDocument(DEPTID!!)
        }, 500)
    }

    fun newInstance(): PolicyListFragment {
        val args = Bundle()
        val fragment = PolicyListFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}
