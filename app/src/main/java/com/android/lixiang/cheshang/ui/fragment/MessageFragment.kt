package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.ui.fragment.BaseMvpFragment

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.MessagePresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetMessageListBean
import com.android.lixiang.cheshang.presenter.data.greenDao.MsgDao
import com.android.lixiang.cheshang.presenter.injection.component.DaggerMessageFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.MessageModule
import com.android.lixiang.cheshang.presenter.view.MessageView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.ui.adapter.MessageAdapter
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : BaseMvpFragment<MessagePresenter>(), MessageView {
    override fun returnGetMessageList(getMessageListBean: GetMessageListBean) {

    }

    override fun injectComponent() {
        DaggerMessageFragmentComponent.builder().fragmentComponent(fragmentComponent).messageModule(MessageModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val mTitleList: MutableList<String>? = mutableListOf()
        val mDetailList: MutableList<String>? = mutableListOf()
        val mTimeList: MutableList<String>? = mutableListOf()
        val mIsReadList: MutableList<String>? = mutableListOf()
        val mIdList: MutableList<String>? = mutableListOf()

        var count: Int? = 0
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val size = (activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll().size
        for (i in 0 until size) {
            mNotFoundRL.visibility = View.GONE
            mMessageLV.visibility = View.VISIBLE
            mNotReadRL.visibility = View.VISIBLE
            mTitleList!!.add("")
            mTitleList.add((activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll()[size - 1 - i].name)
            mDetailList!!.add("")
            mDetailList.add((activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll()[size - 1 - i].detail)
            mTimeList!!.add((activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll()[size - 1 - i].time)
            mIsReadList!!.add("")
            mIsReadList.add((activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll()[size - 1 - i].isRead)
            mTimeList.add("")
            mIdList!!.add("")
            mIdList.add((activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll()[size - 1 - i].msgId)

            if ((activity!!.application as CheshangApplication).getDaoSession().msgDao.loadAll()[i].isRead == "false")
                count = count!! + 1
        }
        if (mTitleList!!.size != 0) {
            val adapter = MessageAdapter(mTitleList, mDetailList, mTimeList, mIsReadList, context)
            if (mMessageLV.headerViewsCount == 0) {
                mMessageLV.addHeaderView(layoutInflater.inflate(R.layout.msg_header, null))
                mMessageLV.setHeaderDividersEnabled(false)
            }
            mMessageLV.adapter = adapter
            mMessageLV.setOnItemClickListener { adapterView, view, i, l ->

                var index = i - 1
                val joes = (activity!!.application as CheshangApplication).getDaoSession().msgDao.queryBuilder().where(MsgDao.Properties.MsgId.eq(mIdList!![index])).list()
                val msg = (activity!!.application as CheshangApplication).getDaoSession().msgDao.load(joes[0].id)
                msg.isRead = "true"
                (activity!!.application as CheshangApplication).getDaoSession().msgDao.update(msg)
                val fragment = MessageDetailFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", "MessageFragment")
                bundle.putString("TITLE", mTitleList[index])
                bundle.putString("DETAIL", mDetailList!![index])
                bundle.putString("TIME", mTimeList!![index - 1])
                fragment.arguments = bundle
                (parentFragment as IndexFragment).start(fragment)
            }
            if (count == 0)
                mNotReadRL.visibility = View.GONE
            else
                mNotReadRL.visibility = View.VISIBLE

            mReadTV.text = String.format("%d条 未读", count)
        } else {
            mNotFoundRL.visibility = View.VISIBLE
            mMessageLV.visibility = View.GONE
            mNotReadRL.visibility = View.GONE
        }
    }

    fun newInstance(): MessageFragment {
        val args = Bundle()
        val fragment = MessageFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        initViews()
    }

    override fun onBackPressedSupport(): Boolean {
        (parentFragment as IndexFragment).changeFragment(0)
        (parentFragment as IndexFragment).page3Topage1()
        return true
    }
}
