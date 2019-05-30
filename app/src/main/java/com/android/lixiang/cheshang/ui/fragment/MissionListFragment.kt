package com.android.lixiang.cheshang.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.annotation.FloatRange
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_mission_list.*
import android.text.Spannable
import android.text.style.AbsoluteSizeSpan
import android.text.SpannableString
import com.android.lixiang.base.utils.view.DimenUtil
import com.blankj.utilcode.util.TimeUtils
import android.text.style.ForegroundColorSpan
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.ui.fragment.BaseSwipeMvpFragment
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.R.id.mMissionListListView
import com.android.lixiang.cheshang.presenter.MissionListPresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.presenter.data.greenDao.Answer
import com.android.lixiang.cheshang.presenter.injection.component.DaggerMissionListFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.MissionListModule
import com.android.lixiang.cheshang.presenter.view.MissionListView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.util.ToastUtil
import com.android.lixiang.cheshang.ui.adapter.MissionListAdapter
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.example.lixiang.testalertdialog.LoadingDialog2
import com.orhanobut.logger.Logger
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SwipeBackLayout
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.fragmentation_swipeback.core.ISwipeBackFragment


class MissionListFragment : BaseMvpFragment<MissionListPresenter>(), MissionListView, View.OnClickListener
//        , ISwipeBackFragmen
{
//    override fun getSwipeBackLayout(): SwipeBackLayout {
//        return swipeBackLayout
//    }
//
//    override fun attachToSwipeBack(view: View?): View {
//        return attachToSwipeBack(view);
//    }
//
//    override fun setSwipeBackEnable(enable: Boolean) {
//        setSwipeBackEnable(enable)
//    }
//
//    override fun setEdgeLevel(edgeLevel: SwipeBackLayout.EdgeLevel) {
//        setEdgeLevel(edgeLevel)
//    }
//
//    override fun setEdgeLevel(widthPixel: Int) {
//        setEdgeLevel(widthPixel)
//    }
//
//    /**
//     * Set the offset of the parallax slip.
//     */
//    override fun setParallaxOffset(@FloatRange(from = 0.0, to = 1.0) offset: Float) {
//        setParallaxOffset(offset)
//    }


    override fun returnGetMissionListError() {
//        dialog!!.dismiss()
        LoadingDialog2(activity!!).hideDialog(dialog2!!)

        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    override fun returnGetMissionList(getMissionListBean: GetMissionListBean) {
        val mTitleList: MutableList<String>? = mutableListOf()
        val mDetailList: MutableList<String>? = mutableListOf()
        val mTagList: MutableList<String>? = mutableListOf()
        doneCount = 0
        if (getMissionListBean.message == "success") {
            mGetMissionListBean = getMissionListBean

            for (i in 0 until getMissionListBean.data.size) {
                mTitleList!!.add(getMissionListBean.data[i].name)
                mNumberList!!.add(getMissionListBean.data[i].questionList.size)
                doneNumbers!!.add(0)
                if (getMissionListBean.data[i].finish == 0) {
                    mDetailList!!.add(String.format("还有 %d 项 未完成", getMissionListBean.data[i].quesAmount - doneNumbers!![i]))
                } else {
                    mDetailList!!.add(("任务已全部完成"))
                    doneCount = doneCount!! + 1
                }
                mTagList!!.add("每日必填")

                for (j in 0 until getMissionListBean.data[i]!!.questionList.size) {
                    ANSWERS[getMissionListBean.data[i]!!.questionList[j].id] = ""
                    val mAnswer = Answer()
                    mAnswer.id = "" + getMissionListBean.data[i]!!.missionId + getMissionListBean.data[i]!!.questionList[j].id.toString()
                    mAnswer.answer = ""
                    mAnswer.questionId = getMissionListBean.data[i]!!.questionList[j].getquesId()
                    (activity!!.application as CheshangApplication).getDaoSession().answerDao.insertOrReplace(mAnswer)
                }
            }
            val adapter = MissionListAdapter(context, mTitleList, mDetailList, mTagList)
            mMissionListListView.adapter = adapter
            mCalendarRL.visibility = View.VISIBLE
//            dialog!!.dismiss()
            LoadingDialog2(activity!!).hideDialog(dialog2!!)

            val mSharedPreferences: SharedPreferences? = activity!!.getSharedPreferences("DONEMISSIONCOUNT", Context.MODE_PRIVATE)
            val editor = mSharedPreferences!!.edit()
            editor.putString("donemissioncount", doneCount!!.toString())
            editor.apply()
        } else if (getMissionListBean.status == 103) {
//            dialog!!.dismiss()
            LoadingDialog2(activity!!).hideDialog(dialog2!!)

            mMissionListListView.visibility = View.GONE
            mCalendarRL.visibility = View.VISIBLE
            mNotFoundRL.visibility = View.VISIBLE
        }
    }

    override fun injectComponent() {
        DaggerMissionListFragmentComponent.builder().fragmentComponent(fragmentComponent).missionListModule(MissionListModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onClick(v: View?) {
        when (v) {
            mCalenderBtn -> {
//                start(MissionCalendarFragment().newInstance())
                ToastUtil().toast2(activity!!, "暂未开放")
            }

            mMissionListStoreRL -> {
                val storeFragment = StoreFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("LATITUDE", LATITUDE.toString())
                bundle.putString("LONGITUDE", LONGITUDE.toString())
                bundle.putInt("INDEX", INDEX!!)
                bundle.putString("PAGE_FROM", "OCRListFragment")
                storeFragment.arguments = bundle
                startForResult(storeFragment, 0x009)
            }

            mBackBtn -> {
                pop()
            }
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x009 && resultCode == ISupportFragment.RESULT_OK) {
            if (data.getString("SHOPID") != "") {
                INDEX = data.getInt("INDEX")
                Logger.d(data.getInt("INDEX"))
                mShopTV.text = data.getString("SHOP")
                SHOPID = data.getString("SHOPID")
                val user = (activity!!.application as CheshangApplication).getDaoSession().userDao.load(1)
                user.shopId = SHOPID
                user.shopName = data.getString("SHOP")
                user.shopAddress = data.getString("SHOPADDRESS")
                INDEX = data.getInt("INDEX")
                (activity!!.application as CheshangApplication).getDaoSession().userDao.update(user)
            }
        } else if (requestCode == 0x011 && resultCode == ISupportFragment.RESULT_OK) {
            fromPage = true
            mPresenter.getMessageList(HRACCOUNT!!, SHOPID!!)
        }
    }


    private var HRACCOUNT: String? = null
    private var mGetMissionListBean: GetMissionListBean? = null
    //    private var dialog: LoadingDialog? = null
    private var dialog2: AlertDialog? = null
    private var INDEX: Int? = -1
    private var mLocationClient: LocationClient? = null
    private var myListener = MyLocationListener()
    private var LATITUDE: Double? = 0.0
    private var LONGITUDE: Double? = 0.0
    private var locationFlag: Boolean? = false
    private var doneNumbers: MutableList<Int>? = mutableListOf()
    var SHOPID: String? = null
    private var ANSWERS: MutableMap<Int, String> = mutableMapOf()
    private var fromPage: Boolean? = false
    private var doneCount: Int? = 0
    val mNumberList: MutableList<Int>? = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mission_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
//        val loadBuilder = LoadingDialog.Builder(activity)
//                .setCancelable(false)
//                .setCancelOutside(false)
//                .setShowMessage(false)
//        dialog = loadBuilder.create()
//        dialog!!.show()
        dialog2 = LoadingDialog2(activity!!).showDialog()

    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val mDate = TimeUtils.getNowString().split(" ")
        val mLastLineIndex = mDate[0].lastIndexOf("-")
        val spanString = SpannableString(mDate[0].substring(mLastLineIndex + 1, mDate[0].length) + "日")
        val span = AbsoluteSizeSpan(DimenUtil().dip2px(activity!!, 64F))
        spanString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        mDateTV.text = spanString
        if ((activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName != "")
            mShopTV.text = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopName

        val string = "今日你需要跟踪-个续保客户"
        val spanString2 = SpannableString(string)
        val span2 = ForegroundColorSpan(Color.parseColor("#F73E00"))
        spanString2.setSpan(span2, string.indexOf("踪") + 1, string.indexOf("个") + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        mFollowTV.text = spanString2

        mLocationClient = LocationClient(activity!!.applicationContext)
        mLocationClient!!.registerLocationListener(myListener)
        mLocationClient!!.start()
        SHOPID = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopId

        Handler().postDelayed({
            mPresenter.getMessageList(HRACCOUNT!!, SHOPID!!)
        }, 1000)

        mMissionListListView.setOnItemClickListener { parent, view, position, id ->
            mMissionListListView.isEnabled = false
            val fragment = MissionTodayFragment().newInstance()
            val bundle = Bundle()
            bundle.putSerializable("DATA", mGetMissionListBean!!.data[position])
            bundle.putInt("INDEX", position)
            var count = 0
            for (i in 0 until position) {
                count += mNumberList!![i]
            }
            bundle.putInt("START", count)
            fragment.arguments = bundle
            startForResult(fragment, 0x011)
        }

        mBackBtn.setOnClickListener(this)
        mCalenderBtn.setOnClickListener(this)
        mMissionListStoreRL.setOnClickListener(this)
        HRACCOUNT = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
    }


    fun newInstance(): MissionListFragment {
        val args = Bundle()
        val fragment = MissionListFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation) {
            LATITUDE = location.latitude    //获取纬度信息
            LONGITUDE = location.longitude    //获取经度信息
            if (!locationFlag!!) {
                locationFlag = true
//                mPresenter.getShopByHrAccountAndPosition(HRACCOUNT!!, LATITUDE.toString(), LONGITUDE.toString())
            }
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        mMissionListListView.isEnabled = true

    }
}
