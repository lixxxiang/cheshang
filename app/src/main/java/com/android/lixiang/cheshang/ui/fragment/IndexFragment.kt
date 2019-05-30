package com.android.lixiang.cheshang.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout

import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.util.CheshangApplication
//import com.android.lixiang.cheshang.R.id.mShadow
//import com.android.lixiang.cheshang.R.id.mShadow2
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_check.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.fragment_work.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*

class IndexFragment : SupportFragment() {

    private var mBottomBar: LinearLayout? = null
    private val mWorkFragment by lazy { WorkFragment() }
    private val mCheckFragment by lazy { CheckFragment() }
    private val mMessageFragment by lazy { MessageFragment() }
    private val mMeFragment by lazy { MeFragment() }
    var fragment: IndexFragment? = null
    private val mStack = Stack<Fragment>()
    var SHOPID: String? = null
    var INDEX: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x005 && resultCode == ISupportFragment.RESULT_OK) {
            var flag = data.getString("STATUS")
            if (flag == "IN") {
                mCheckInRL.visibility = View.GONE
                mCheckOutRL.visibility = View.VISIBLE
            } else if (flag == "OUT") {
                mCheckInRL.visibility = View.VISIBLE
                mCheckOutRL.visibility = View.GONE
            }
//            mRepeatTV.text = repeatBack
//            mIndex = data.getInt("REPEATINDEX")
        } else if (requestCode == 0x006 && resultCode == ISupportFragment.RESULT_OK) {
            if(data.getString("SHOPID")!= ""){
                mCurrentShopTV.text = data.getString("SHOP")
                SHOPID = data.getString("SHOPID")
                val user = (activity!!.application as CheshangApplication).getDaoSession().userDao.load(1)
                user.shopId = SHOPID
                user.shopName = data.getString("SHOP")
                user.shopAddress = data.getString("SHOPADDRESS")
                INDEX = data.getInt("INDEX")
                (activity!!.application as CheshangApplication).getDaoSession().userDao.update(user)
            }else{
//                mCurrentShopTV.text = "附近无车商店"
            }

        } else if (requestCode == 0x007 && resultCode == ISupportFragment.RESULT_OK) {
            Logger.d(data.getString("USERNAME"))
            mUserNameTV.text = String.format("HI! %s", data.getString("USERNAME"))
        }
    }

    fun getShopId(): String? {
        return SHOPID
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBottomBar = mBottomNavigationView.findViewById(R.id.mBottomView)
        initViews()
        initFragments()
        initNavigationViews()
        fragment = this
        changeFragment(0)
    }

    private fun initNavigationViews() {
        val mIcon1 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon1)
        val mIcon2 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon2)
        val mIcon3 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon3)
        val mIcon4 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon4)

        val mTitle1 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle1)
        val mTitle2 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle2)
        val mTitle3 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle3)
        val mTitle4 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle4)

        val mField1 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField1)
        val mField2 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField2)
        val mField3 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField3)
        val mField4 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField4)

        if (mIcon1 != null)
            mIcon1.setBackgroundResource(R.drawable.ic_nav_home_highlight)
        if (mTitle1 != null && activity != null)
            mTitle1.setTextColor(ContextCompat.getColor(activity!!, R.color.colorMain))

        mField1.setOnClickListener {
            //            mShadow.visibility = View.VISIBLE
//            mShadow2.visibility = View.GONE
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            mIcon1.setBackgroundResource(R.drawable.ic_nav_home_highlight)
            mIcon2.setBackgroundResource(R.drawable.ic_nav_check)
            mIcon3.setBackgroundResource(R.drawable.ic_nav_message)
            mIcon4.setBackgroundResource(R.drawable.ic_nav_me)
            mTitle1.setTextColor(ContextCompat.getColor(activity!!, R.color.colorMain))
            mTitle2.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            mTitle3.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            mTitle4.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            changeFragment(0)
        }

        mField2.setOnClickListener {
            //            mShadow.visibility = View.GONE
//            mShadow2.visibility = View.VISIBLE
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            mIcon1.setBackgroundResource(R.drawable.ic_nav_home)
            mIcon2.setBackgroundResource(R.drawable.ic_nav_check_highlight)
            mIcon3.setBackgroundResource(R.drawable.ic_nav_message)
            mIcon4.setBackgroundResource(R.drawable.ic_nav_me)
            mTitle1.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            mTitle2.setTextColor(ContextCompat.getColor(activity!!, R.color.colorMain))
            mTitle3.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            mTitle4.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            changeFragment(1)
        }

        mField3.setOnClickListener {
            //            mShadow.visibility = View.VISIBLE
//            mShadow2.visibility = View.GONE
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            mIcon1.setBackgroundResource(R.drawable.ic_nav_home)
            mIcon2.setBackgroundResource(R.drawable.ic_nav_check)
            mIcon3.setBackgroundResource(R.drawable.ic_nav_message_highlight)
            mIcon4.setBackgroundResource(R.drawable.ic_nav_me)
            mTitle1.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            mTitle2.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            mTitle3.setTextColor(ContextCompat.getColor(activity!!, R.color.colorMain))
            mTitle4.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            changeFragment(2)
        }

        mField4.setOnClickListener {
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            mShadow.visibility = View.GONE
//            mShadow2.visibility = View.VISIBLE
            mIcon1.setBackgroundResource(R.drawable.ic_nav_home)
            mIcon2.setBackgroundResource(R.drawable.ic_nav_check)
            mIcon3.setBackgroundResource(R.drawable.ic_nav_message)
            mIcon4.setBackgroundResource(R.drawable.ic_nav_me_highlight)
            mTitle1.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            mTitle2.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            mTitle3.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
            mTitle4.setTextColor(ContextCompat.getColor(activity!!, R.color.colorMain))
            changeFragment(3)

        }
    }

    fun changeFragment(position: Int) {
        val manager = childFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }
        manager.show(mStack[position])
        manager.commit()
    }

    fun page1Topage2() {
//        mShadow.visibility = View.GONE
//        mShadow2.visibility = View.VISIBLE
        val mIcon1 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon1)
        val mIcon2 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon2)
        val mTitle1 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle1)
        val mTitle2 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle2)
        mIcon1.setBackgroundResource(R.drawable.ic_nav_home)
        mIcon2.setBackgroundResource(R.drawable.ic_nav_check_highlight)
        mTitle1.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
        mTitle2.setTextColor(ContextCompat.getColor(activity!!, R.color.colorMain))
    }

    fun page2Topage1() {
//        mShadow.visibility = View.GONE
//        mShadow2.visibility = View.VISIBLE
        val mIcon1 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon1)
        val mIcon2 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon2)
        val mTitle1 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle1)
        val mTitle2 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle2)
        mIcon1.setBackgroundResource(R.drawable.ic_nav_home_highlight)
        mIcon2.setBackgroundResource(R.drawable.ic_nav_check)
        mTitle1.setTextColor(ContextCompat.getColor(activity!!, R.color.colorMain))
        mTitle2.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
    }

    fun page1Topage3() {
        val mIcon1 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon1)
        val mIcon3 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon3)
        val mTitle1 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle1)
        val mTitle3 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle3)
        mIcon1.setBackgroundResource(R.drawable.ic_nav_home)
        mIcon3.setBackgroundResource(R.drawable.ic_nav_message_highlight)
        mTitle1.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
        mTitle3.setTextColor(ContextCompat.getColor(activity!!, R.color.colorMain))
    }

    fun page3Topage1() {
        val mIcon1 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon1)
        val mIcon3 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon3)
        val mTitle1 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle1)
        val mTitle3 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle3)
        mIcon1.setBackgroundResource(R.drawable.ic_nav_home_highlight)
        mIcon3.setBackgroundResource(R.drawable.ic_nav_message)
        mTitle1.setTextColor(ContextCompat.getColor(activity!!, R.color.colorMain))
        mTitle3.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
    }

    fun page4Topage1() {
        val mIcon1 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon1)
        val mIcon4 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon4)
        val mTitle1 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle1)
        val mTitle4 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle4)
        mIcon1.setBackgroundResource(R.drawable.ic_nav_home_highlight)
        mIcon4.setBackgroundResource(R.drawable.ic_nav_me)
        mTitle1.setTextColor(ContextCompat.getColor(activity!!, R.color.colorMain))
        mTitle4.setTextColor(ContextCompat.getColor(activity!!, R.color.colorTextGray))
    }

    private fun initFragments() {
        val manager = childFragmentManager.beginTransaction()
        manager.add(R.id.mFrameLayout, mWorkFragment)
        manager.add(R.id.mFrameLayout, mCheckFragment)
        manager.add(R.id.mFrameLayout, mMessageFragment)
        manager.add(R.id.mFrameLayout, mMeFragment)
        manager.commit()
        mStack.add(mWorkFragment)
        mStack.add(mCheckFragment)
        mStack.add(mMessageFragment)
        mStack.add(mMeFragment)
    }

    private fun initViews() {

    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }

    fun newInstance(): IndexFragment {
        val args = Bundle()
        val fragment = IndexFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultVerticalAnimator()
    }

}
