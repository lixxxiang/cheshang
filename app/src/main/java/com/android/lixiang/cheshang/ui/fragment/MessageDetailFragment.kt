package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.cheshang.R
import kotlinx.android.synthetic.main.fragment_message_detail.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class MessageDetailFragment: SupportFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mMessageDetailToolbar.title = "消息详情"
        (activity as AppCompatActivity).setSupportActionBar(mMessageDetailToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if(arguments!!.getString("PAGE_FROM") == "MessageFragment"){
            mTitleTV.text = arguments!!.getString("TITLE")
            mDetailTV.text = arguments!!.getString("DETAIL")
            mTimeTV.text = arguments!!.getString("TIME")
        }else{
            mTitleTV.text = arguments!!.getString("NAME")
            mDetailTV.text = arguments!!.getString("DETAIL")
            mTimeTV.text = arguments!!.getString("TIME")
        }
        mMessageDetailToolbar.setNavigationOnClickListener {
            if(arguments!!.getString("PAGE_FROM") == "MessageFragment"){
                pop()
            }else{
                startWithPop(IndexFragment().newInstance())
            }
        }
    }

    fun newInstance(): MessageDetailFragment {
        val args = Bundle()
        val fragment = MessageDetailFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onBackPressedSupport(): Boolean {
        if(arguments!!.getString("PAGE_FROM") == "MessageFragment"){
            return super.onBackPressedSupport()
        }else{
            startWithPop(IndexFragment().newInstance())
            return true
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}
