package com.android.lixiang.cheshang.ui.fragment

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.WorkRecordPresenter
import com.android.lixiang.cheshang.presenter.data.greenDao.Record
import com.android.lixiang.cheshang.presenter.data.greenDao.RecordDao
import com.android.lixiang.cheshang.presenter.injection.component.DaggerWorkRecordFragmentComponent
import com.android.lixiang.cheshang.presenter.injection.module.WorkRecordModule
import com.android.lixiang.cheshang.presenter.view.WorkRecordView
import com.android.lixiang.cheshang.util.AlarmService
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.ui.adapter.WorkRecordAdapter
import com.blankj.utilcode.util.TimeUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_work_record.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.text.SimpleDateFormat
import java.util.*

class WorkRecordFragment : BaseMvpFragment<WorkRecordPresenter>(), WorkRecordView, View.OnClickListener {
    override fun injectComponent() {
        DaggerWorkRecordFragmentComponent.builder().fragmentComponent(fragmentComponent).workRecordModule(WorkRecordModule()).build().inject(this)
        mPresenter.mView = this
    }

    private var details: MutableList<String>? = mutableListOf()
    private var mAdapter: WorkRecordAdapter? = null
    private var mEditState: Boolean? = false
    private var count: Int? = 0
    private var Ls_D: MutableList<Boolean>? = mutableListOf()
    private var Ls: MutableList<String>? = mutableListOf()
    private var fragment: WorkRecordFragment? = null
    private var mRecordList: MutableList<Record>? = mutableListOf()
    private var mDeleteList: MutableList<Int>? = mutableListOf()
    private var mDeleteIndexList: MutableList<Long>? = mutableListOf()
    private var mPrimeIdList: MutableList<Long>? = mutableListOf()
    private var SIZE: Int? = null
    private var LASTINDEX: Long? = 0
    private var alarmSet: String? = null
    private var alarmSetIndex: String? = null
    private var alarmSetIndexMax: String? = null

    override fun onClick(v: View?) {
        when (v) {
            mEditBtn -> {
                if (!mEditState!!) {
                    mEditBtn.text = "完成"
                    mDeleteRL.visibility = View.VISIBLE
                    mAdapter!!.isEdit()
                    mEditState = true
                } else {
                    mEditBtn.text = "编辑"
                    mDeleteRL.visibility = View.GONE
                    mAdapter!!.isNotEdit()
                    mEditState = false
                }
            }
            mDeleteRL -> {
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle("确认要删除日志吗？")
                builder.setPositiveButton(getString(R.string.confirm)) { dialog, _ ->
                    dialog.dismiss()
                    for (i in 0 until mDeleteList!!.size) {
                        if (mDeleteList!![i] == 0) {
                            mDeleteIndexList!!.add((activity!!.application as CheshangApplication).getDaoSession().recordDao.loadAll()[i].id)
                        }
                    }
                    for (i in 0 until mDeleteIndexList!!.size) {
                        (activity!!.application as CheshangApplication).getDaoSession().recordDao.deleteByKey(mDeleteIndexList!![i])
                    }
                    initList()
                }

                builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }

                builder.create().show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_record, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mDateTV.text = Utils().timeFormat()
        fragment = this

        initList()
        mAdapter!!.notifyDataSetInvalidated()
        mWorkRecordLV.setOnItemClickListener { parent, view, position, id ->
            if (mEditState!!) {//编辑状态
                if (position < SIZE!!) {
                    mAdapter!!.select(position)

                    if (mDeleteList!![position] == -1) {
                        mDeleteList!![position] = 0
                    } else if (mDeleteList!![position] == 0) {
                        mDeleteList!![position] = -1
                    }

                    mAdapter!!.notifyDataSetChanged()
                }
            } else {//非编辑状态
                if (position == SIZE) {
                    val fragment: RecordDetailFragment = RecordDetailFragment().newInstance()
                    val bundle: Bundle? = Bundle()
                    bundle!!.putString("CONTENT", "")
                    bundle.putString("TIME", "")
                    bundle.putString("REPEAT", "")
                    bundle.putString("FIRST_IN", "true")//新建
                    bundle.putString("INDEX", (LASTINDEX!! + 1).toString())
                    fragment.arguments = bundle
                    startForResult(fragment, 0x003)
                } else if (position < SIZE!!) {
                    Logger.d(mPrimeIdList!![position])
                    val fragment: RecordDetailFragment = RecordDetailFragment().newInstance()
                    val bundle: Bundle? = Bundle()
                    bundle!!.putString("CONTENT", mRecordList!![position].detail)
                    bundle.putString("TIME", mRecordList!![position].time)
                    bundle.putString("REPEAT", mRecordList!![position].remind)
                    bundle.putString("FIRST_IN", "false")//非新建
                    bundle.putString("INDEX", mPrimeIdList!![position].toString())
                    fragment.arguments = bundle
                    startForResult(fragment, 0x003)
                }
            }
        }
        search_back_iv.setOnClickListener {
            pop()
        }

        mEditBtn.setOnClickListener(this)
        mDeleteRL.setOnClickListener(this)

    }


    private fun initList() {
        SIZE = (activity!!.application as CheshangApplication).getDaoSession().recordDao.loadAll().size
        if ((activity!!.application as CheshangApplication).getDaoSession().recordDao.loadAll().size > 0) {
            mEditBtn.visibility = View.VISIBLE
        }
        for (i in 0 until (activity!!.application as CheshangApplication).getDaoSession().recordDao.loadAll().size) {
            mDeleteList!!.add(-1)
            mPrimeIdList!!.add((activity!!.application as CheshangApplication).getDaoSession().recordDao.loadAll()[i].id)
            Ls_D!!.add(false)

        }
        mRecordList = (activity!!.application as CheshangApplication).getDaoSession().recordDao.loadAll()
        for (i in 0 until mRecordList!!.size) {
            if (mRecordList!![i].id > LASTINDEX!!)
                LASTINDEX = mRecordList!![i].id
        }
        mAdapter = WorkRecordAdapter(mRecordList, context, fragment)
        mWorkRecordLV.adapter = mAdapter

        mAdapter!!.notifyDataSetChanged()

        Handler().postDelayed({
            var count = 0
            for (i in 0 until mAdapter!!.count().size) {
                if (mAdapter!!.count()[i]) {
                    ++count
                }
                if ((activity!!.application as CheshangApplication).getDaoSession().recordDao.load((i + 1).toLong()) != null) {
                    val record = (activity!!.application as CheshangApplication).getDaoSession().recordDao.load((i + 1).toLong())
                    record.isToday = mAdapter!!.count()[i].toString()
                    (activity!!.application as CheshangApplication).getDaoSession().recordDao.insertOrReplace(record)
                    setAlarm()

                }
            }
            mSubTitleTV.text = String.format("今天%d个项目未到期", count)
        }, 100)
    }


    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x003 && resultCode == ISupportFragment.RESULT_OK) {
            if (data.getString("FIRST_IN") == "false") {//不是第一次进 就是修改 修改后不用刷新
                initList()
            } else {
                initList()
            }
        }
    }

    fun newInstance(): WorkRecordFragment {
        val args = Bundle()
        val fragment = WorkRecordFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setAlarm() {
        val times: MutableList<String>? = mutableListOf()
        val sp = activity!!.getSharedPreferences("ALARMSET", Context.MODE_PRIVATE)
//        alarmSet = sp.getString("alarmset", "")
//        alarmSetIndex = sp.getString("alarmsetIndex", "")
        alarmSetIndexMax = sp.getString("alarmsetIndexMax", "")
        if(alarmSetIndexMax == "")
            alarmSetIndexMax = "0"

        val list = (activity!!.application as CheshangApplication).getDaoSession().recordDao.queryBuilder().where(RecordDao.Properties.IsToday.eq("true")).list()
        Logger.d(list)

        if (!list.isEmpty()) {
            for (i in 0 until list.size) {
                if (list[i].realTime != "") {
                    times!!.add(list[i].realTime)
                    details!!.add(list[i].detail)
                }
            }

            Logger.d(times)
            val year = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd")).split("-")[0]
            val month = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd")).split("-")[1]
            val day = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd")).split("-")[2]
            val intentArray: ArrayList<PendingIntent> = ArrayList()
            val alarmManager = arrayOfNulls<AlarmManager>(4)
            Logger.d(alarmSetIndexMax)
            for (i in alarmSetIndexMax!!.toInt() until times!!.size) {
                val time = times[i].split(" ")[1]

                val intent = Intent(activity, AlarmService::class.java)
                intent.putExtra("TIME", times[i])
                intent.putExtra("DETAIL", details!![i])
                intent.action = AlarmService.ACTION_ALARM

                val calendar = Calendar.getInstance()
                calendar.set(year.toInt(), month.toInt() - 1, day.toInt(), time.split(":")[0].toInt(), time.split(":")[1].toInt() - 5, 0)
                val pendingIntent = PendingIntent.getService(activity, i, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                alarmManager[i] = activity!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val diff = System.currentTimeMillis() - calendar.timeInMillis
                if (diff < 0)
                    alarmManager[i]!!.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                intentArray.add(pendingIntent)
            }
            var mSharedPreferences: SharedPreferences? = null
            mSharedPreferences = activity!!.getSharedPreferences("ALARMSET", Context.MODE_PRIVATE)
            val editor = mSharedPreferences!!.edit()
//            editor.putString("alarmset", alarmSet!!.substring(0, alarmSet!!.length - 1))
//            editor.putString("alarmsetIndex", alarmSetIndex!!.substring(0, alarmSet!!.length - 1))
            editor.putString("alarmsetIndexMax", (times!!.size - 1).toString())
            editor.apply()
        }
    }

}
