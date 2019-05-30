package com.android.lixiang.cheshang.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.MissionTodayPresenter
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswer
import com.android.lixiang.cheshang.presenter.data.bean.SubmitMissionAnswerBean
import com.android.lixiang.cheshang.presenter.injection.component.DaggerMissionTodayFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.MissionTodayModule
import com.android.lixiang.cheshang.presenter.view.MissionTodayView
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.DoneDialog
import com.android.lixiang.cheshang.util.LoadingDialog
import com.android.lixiang.cheshang.util.ToastUtil
import com.android.lixiang.cheshang.ui.adapter.LeftListViewAdapter
import com.blankj.utilcode.util.KeyboardUtils
import com.example.lixiang.testalertdialog.LoadingDialog2
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_mission_today.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.textColor
import java.util.*

class MissionTodayFragment : BaseMvpFragment<MissionTodayPresenter>(), MissionTodayView, View.OnClickListener {
    override fun returnSubmitMissionAnswerError() {
//        dialog!!.dismiss()
        LoadingDialog2(activity!!).hideDialog(dialog3!!)
        ToastUtil().toast2(activity!!, "网络连接失败，请检查网络设置")
    }

    override fun onClick(v: View?) {
        when (v) {
            mDoneBtn -> {
                KeyboardUtils.hideSoftInput(activity)
                if (!checkDone()) {
//                    val builder = AlertDialog.Builder(activity!!)
//                    builder.setTitle("请完成所有任务，再点击完成")
//                    builder.create().show()
                    ToastUtil().toast(activity!!, "请完成所有任务，再点击完成")
                } else {
//                    val loadBuilder = LoadingDialog.Builder(activity)
//                            .setCancelable(false)
//                            .setCancelOutside(false)
//                            .setShowMessage(false)
//                    dialog = loadBuilder.create()
//                    dialog!!.show()
                    dialog3 = LoadingDialog2(activity!!).showDialog()
                    var size = (SIZE!! + arguments!!.getInt("START"))
                    for (i in arguments!!.getInt("START") until size) {
                        val submitMissionAnswer = SubmitMissionAnswer()
                        submitMissionAnswer.answer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.loadAll()[i].answer
                        submitMissionAnswer.hrAccount = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].hrAccount
                        submitMissionAnswer.quesId = (activity!!.application as CheshangApplication).getDaoSession().answerDao.loadAll()[i].questionId
                        submitMissionAnswer.shopId = (activity!!.application as CheshangApplication).getDaoSession().userDao.loadAll()[0].shopId
                        mSubmitMissionAnswerList.add(submitMissionAnswer)
                    }

                    SubmitMissionAnswerArray = mSubmitMissionAnswerList.toTypedArray()
                    mPresenter.submitMissoonAnswer(SubmitMissionAnswerArray)
                }
            }
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


    }

    override fun injectComponent() {
        DaggerMissionTodayFragmentComponent.builder().fragmentComponent(fragmentComponent).missionTodayModule(MissionTodayModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnSubmitMissionAnswer(submitMissionAnswerBean: SubmitMissionAnswerBean) {
        if (submitMissionAnswerBean.status == 200) {
//            dialog!!.dismiss()
            LoadingDialog2(activity!!).hideDialog(dialog3!!)

//            val DoneDialog = DoneDialog.Builder(activity)
//                    .setCancelable(false)
//                    .setCancelOutside(false)
//                    .setShowMessage(false)
//            dialog2 = DoneDialog.create()
//            dialog2!!.show()
//            Handler().postDelayed({
//                dialog2!!.dismiss()
                val bundle = Bundle()
                bundle.putInt("INDEX", arguments!!.getInt("INDEX"))
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                pop()
                ToastUtil().toast2(activity!!, "任务提交成功")
//            }, 1000)
        }
    }

    private var adapter: LeftListViewAdapter? = null
    private var scrollPosition = -1
    private var mGetMissionListDataBean: GetMissionListBean.DataBean? = null
    private var addFlag = true
    private var SIZE: Int? = 0
    private var ANSWERS: MutableMap<Int, String> = mutableMapOf()
    private var SubmitMissionAnswerArray: Array<SubmitMissionAnswer> = emptyArray()
    private var mSubmitMissionAnswerList: MutableList<SubmitMissionAnswer> = mutableListOf()
    //    private var dialog: LoadingDialog? = null
    private var dialog3: AlertDialog? = null
//    private var dialog2: DoneDialog? = null
    private var doneFlag: MutableList<Boolean>? = mutableListOf()
    private var count: Int? = 0
    private var MISSIONIDS: MutableList<String>? = mutableListOf()
    private var REQUIRED: MutableList<Int>? = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mission_today, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    override fun onBackPressedSupport(): Boolean {
//        val bundle = Bundle()
//        bundle.putInt("INDEX", -1)
//        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
//
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("未完成全部任务，返回将丢失已填数据")
        builder.setPositiveButton("确认返回") { dialog, _ ->
            dialog.dismiss()
            KeyboardUtils.hideSoftInput(activity!!)

            val bundle = Bundle()
            bundle.putInt("INDEX", -1)
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            (activity!!.application as CheshangApplication).getDaoSession().answerDao.deleteAll()
            pop()
        }
        builder.setNegativeButton("取消") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
        return true
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mMissionTodayToolbar.title = "今日任务"


        (activity as AppCompatActivity).setSupportActionBar(mMissionTodayToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mMissionTodayToolbar.setNavigationOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle("未完成全部任务，返回将丢失已填数据")
            builder.setPositiveButton("确认返回") { dialog, _ ->
                KeyboardUtils.hideSoftInput(activity!!)
                dialog.dismiss()
                val bundle = Bundle()
                bundle.putInt("INDEX", arguments!!.getInt("INDEX"))
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                (activity!!.application as CheshangApplication).getDaoSession().answerDao.deleteAll()
                pop()
            }
            builder.setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }

        mDoneBtn.setOnClickListener(this)
        mGetMissionListDataBean = arguments!!.getSerializable("DATA") as GetMissionListBean.DataBean
        SIZE = mGetMissionListDataBean!!.questionList.size
        for (i in 0 until SIZE!!) {
            doneFlag!!.add(false)
            REQUIRED!!.add(mGetMissionListDataBean!!.questionList[i].required)
        }

        mMissionTodayToolbar.subtitle = String.format("已完成：%d/%d", 0, SIZE)

//        if ((activity!!.application as CheshangApplication).getDaoSession().answerDao.loadAll().isEmpty()) {
//            for (i in 0 until mGetMissionListDataBean!!.questionList.size) {
//                ANSWERS[mGetMissionListDataBean!!.questionList[i].id] = ""
//                val mAnswer = Answer()
//                mAnswer.id = "" + mGetMissionListDataBean!!.missionId + mGetMissionListDataBean!!.questionList[i].id.toString()
//                mAnswer.answer = ""
//                mAnswer.questionId = mGetMissionListDataBean!!.questionList[i].id.toString()
//                (activity!!.application as CheshangApplication).getDaoSession().answerDao.insertOrReplace(mAnswer)
//            }
//        }


        verticalViewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                KeyboardUtils.hideSoftInput(activity)
            }

            override fun onPageSelected(firstVisibleItem: Int) {
                addFlag = true

                if (scrollPosition != firstVisibleItem) {
                    adapter!!.setSelectItem(firstVisibleItem)
                    mLeftLV!!.setSelectionFromTop(firstVisibleItem, 40)
                    scrollPosition = firstVisibleItem
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        verticalViewPager.adapter = DummyAdapter(childFragmentManager, mGetMissionListDataBean!!, SIZE!!, ANSWERS)
        verticalViewPager.currentItem = 0
        verticalViewPager.offscreenPageLimit = SIZE!!

        adapter = LeftListViewAdapter(context, SIZE!!)
        mLeftLV!!.adapter = adapter
        mLeftLV!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            KeyboardUtils.hideSoftInput(activity)
            Handler().postDelayed({
                verticalViewPager.currentItem = position
            }, 300)
            adapter!!.setSelectItem(position)
            addFlag = true
        }
    }

    private fun checkDone(): Boolean {
        var flag = true
        for (i in 0 until doneFlag!!.size) {
            if (REQUIRED!![i] != 2) {
                if (!doneFlag!![i] || REQUIRED!![i] == 2) {
                    flag = false
                }
            }
        }
        return flag
    }

    fun optionClick(index: Int) {
        doneFlag!![index] = true
        adapter!!.select(index)
        if (addFlag) {
            addFlag = false
        }
        var mDoneCount = 0

        for (i in 0 until SIZE!!) {
            if (doneFlag!![i]) {
                ++mDoneCount
            }
        }
        count = mDoneCount
        mMissionTodayToolbar.subtitle = String.format("已完成：%d/%d", mDoneCount, SIZE)
        adapter!!.notifyDataSetInvalidated()
        if (checkDone()) {
            mDoneBtn.textColor = Color.parseColor("#F73E00")
        } else
            mDoneBtn.textColor = Color.parseColor("#9B9B9B")
    }

//    fun optionClick2(index: Int) {
//        doneFlag!![index] = true
//        if (addFlag) {
//            addFlag = false
//        }
//        var mDoneCount = 0
//
//        for (i in 0 until SIZE!!) {
//            if (doneFlag!![i]) {
//                ++mDoneCount
//            }
//        }
//        count = mDoneCount
//        mMissionTodayToolbar.subtitle = String.format("已完成：%d/%d", mDoneCount, SIZE)
//        if (checkDone()) {
//            mDoneBtn.textColor = Color.parseColor("#F73E00")
//        } else
//            mDoneBtn.textColor = Color.parseColor("#9B9B9B")
//    }
//
//    fun checkDone(mDoneCount: Int): Boolean {
//        return mDoneCount == SIZE
//    }

    fun optionUnClick(index: Int) {
        doneFlag!![index] = false
        adapter!!.unSelect(index)
        var mDoneCount = 0
        for (i in 0 until SIZE!!) {
            if (doneFlag!![i]) {
                ++mDoneCount
            }
        }
        count = mDoneCount

        mMissionTodayToolbar.subtitle = String.format("已完成：%d/%d", mDoneCount, SIZE)
        adapter!!.notifyDataSetInvalidated()
        if (checkDone()) {
            mDoneBtn.textColor = Color.parseColor("#F73E00")
        } else
            mDoneBtn.textColor = Color.parseColor("#9B9B9B")
        Logger.d(doneFlag)
    }

    fun newInstance(): MissionTodayFragment {
        val args = Bundle()
        val fragment = MissionTodayFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    inner class DummyAdapter(fm: FragmentManager, mGetMissionListDataBean: GetMissionListBean.DataBean, size: Int, ANSWERS: MutableMap<Int, String>) : android.support.v4.app.FragmentPagerAdapter(fm) {
        private var fragments: MutableList<PlaceholderFragment> = ArrayList()

        init {
            for (i in 0 until SIZE!!) {
                fragments.add(PlaceholderFragment().newInstance(i, mGetMissionListDataBean, size, ANSWERS))
            }
        }

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            return fragments[position]
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return SIZE!!
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "PAGE$position"
        }
    }
}
