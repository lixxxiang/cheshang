package com.android.lixiang.cheshang.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.data.greenDao.Record
import com.android.lixiang.cheshang.util.CheshangApplication
import com.android.lixiang.cheshang.util.ToastUtil
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.fragment_record_detail.*
import me.yokeyword.fragmentation.SupportFragment
import java.util.*
import com.blankj.utilcode.util.TimeUtils
import com.orhanobut.logger.Logger
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.text.ParseException
import java.text.SimpleDateFormat


class RecordDetailFragment : SupportFragment(), View.OnClickListener {

    var ca = Calendar.getInstance()
    var mYear = ca.get(Calendar.YEAR)
    var mMonth = ca.get(Calendar.MONTH)
    var mDay = ca.get(Calendar.DAY_OF_MONTH)
    private var mDateAndTime: String? = null
    private val MAX_NUM = 210
    private var mHour: String? = null
    private var mMinute: String? = null
    private var tempString: String? = null
    private var CONTENT: String? = null
    private var TIME: String? = null
    private var REPEAT: String? = null
    private var repeatBack: String? = null
    private var FIRST_IN: String? = null
    private var mContentCommit: String? = null
    private var mTimeCommit: String? = null
    private var mRepeatCommit: String? = null
    private var mRealTime: String? = null
    private var mIndex: Int? = -1
    var startcal = Calendar.getInstance()
    override fun onClick(p0: View?) {
        when (p0) {
            mAlertTimeRL -> {
                DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
            }
            mRepeatRL -> {
                val fragment = RepeatSettingFragment().newInstance()
                val bundle = Bundle()
                bundle.putInt("INDEX", mIndex!!)
                fragment.arguments = bundle
                startForResult(fragment, 0x001)
            }
            mRecordDoneBtn -> {
                KeyboardUtils.hideSoftInput(activity)
                if (mContentET.text.toString().isEmpty()) {
                    ToastUtil().toast(activity!!, "请输入日志内容")
                } else {
                    mContentCommit = mContentET.text.toString()
                    mTimeCommit = if (tempString != "")
                        tempString
                    else
                        timeFormat()
                    mRepeatCommit = if (repeatBack != "")
                        repeatBack
                    else
                        "永不"

                    val bundle = Bundle()
                    val record = Record()
                    record.detail = mContentCommit
                    bundle.putString("COMMENT", mContentCommit)
                    if (mSwitch.isChecked) {//提交时 开关是打开的
                        bundle.putString("TIME", mTimeCommit)
                        bundle.putString("REPEAT", mRepeatCommit)
                        record.time = mTimeCommit
                        if (mRealTime == null) {
                            record.realTime = timeFormat2()
                        } else
                            record.realTime = mRealTime

                        record.remind = mRepeatCommit
                    } else {
                        bundle.putString("TIME", "")
                        bundle.putString("REPEAT", "")
                        record.time = ""
                        record.remind = ""
                        record.realTime = ""
                    }

                    record.id = arguments!!.getString("INDEX").toLong()
                    if (arguments!!.getString("FIRST_IN") == "true") {
                        (activity!!.application as CheshangApplication).getDaoSession().recordDao.insertOrReplace(record)
                        var mSharedPreferences: SharedPreferences? = null
                        mSharedPreferences = activity!!.getSharedPreferences("RECORD", Context.MODE_PRIVATE)
                        val editor = mSharedPreferences!!.edit()
                        editor.putString("record", "null")
                        editor.commit()
                    } else {
                        val mRecordUpdate = (activity!!.application as CheshangApplication).getDaoSession().recordDao.load(arguments!!.getString("INDEX").toLong())
                        Logger.d(mRecordUpdate)
                        if (mSwitch.isChecked) {//提交时 开关是打开的
                            mRecordUpdate.time = mTimeCommit
                            mRecordUpdate.remind = mRepeatCommit
                            if (mRealTime == null) {
                                mRecordUpdate.realTime = timeFormat2()
                            } else
                                mRecordUpdate.realTime = mRealTime

                        } else {
                            mRecordUpdate.time = ""
                            mRecordUpdate.remind = ""
                            mRecordUpdate.realTime = ""

                        }
                        (activity!!.application as CheshangApplication).getDaoSession().recordDao.update(mRecordUpdate)
                    }

                    bundle.putString("INDEX", arguments!!.getString("INDEX"))
                    bundle.putString("FIRST_IN", FIRST_IN)
                    setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                    pop()
                }
            }
        }
    }

    private fun compareData(mContentCommit: String?, mTimeCommit: String?, mRepeatCommit: String?): Boolean {
        return CONTENT == mContentCommit && mTimeCommit == TIME && mRepeatCommit == REPEAT

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x001 && resultCode == ISupportFragment.RESULT_OK) {
            if (data.getString("REPEAT") != "") {
                repeatBack = data.getString("REPEAT")
                mRepeatTV.text = repeatBack
                mIndex = data.getInt("REPEATINDEX")
            }
        }
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mRecordDetailToolbar.title = "详细信息"

        (activity as AppCompatActivity).setSupportActionBar(mRecordDetailToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mRecordDetailToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity)
            val bundle = Bundle()
            bundle.putString("COMMENT", "")
            bundle.putString("TIME", "")
            bundle.putString("REPEAT", "")
            bundle.putString("INDEX", "")
            bundle.putString("FIRST_IN", "")
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
        }

        mSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                KeyboardUtils.hideSoftInput(activity)
                mLine.visibility = View.VISIBLE
                mAlertTimeRL.visibility = View.VISIBLE
                mRepeatRL.visibility = View.VISIBLE

            } else {
                KeyboardUtils.hideSoftInput(activity)
                mRepeatTV.text = "永不"
                mTimeTV.text = timeFormat()
                mAlertTimeRL.visibility = View.GONE
                mRepeatRL.visibility = View.GONE
                mLine.visibility = View.GONE
            }
        }

        mTimeTV.text = timeFormat()
        mDateTV.text = Utils().timeFormat()
        CONTENT = arguments!!.getString("CONTENT")
        TIME = arguments!!.getString("TIME")
        tempString = TIME
        REPEAT = arguments!!.getString("REPEAT")
        repeatBack = REPEAT
        FIRST_IN = arguments!!.getString("FIRST_IN")

        if (CONTENT == "" && TIME == "" && REPEAT == "") {
            Handler().postDelayed({
                mContentET.isFocusable = true
                mContentET.isFocusableInTouchMode = true
                mContentET.requestFocus()
                mContentET.addTextChangedListener(mReportTextWatcher)
                KeyboardUtils.showSoftInput(activity)
            }, 200)
        } else {
            if (CONTENT != "") {
                mContentET.setText(CONTENT)
                val num = MAX_NUM - CONTENT!!.length
                mCountsTV.text = num.toString() + "/210"
            }
            if (TIME != "")
                mTimeTV.text = TIME
            if (REPEAT != "") {
                mRepeatTV.text = REPEAT
                mSwitch.isChecked = true
            }
        }


        mAlertTimeRL.setOnClickListener(this)
        mRepeatRL.setOnClickListener(this)
        mRecordDoneBtn.setOnClickListener(this)
    }

    fun newInstance(): RecordDetailFragment {
        val args = Bundle()
        val fragment = RecordDetailFragment()
        fragment.arguments = args
        return fragment
    }

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            if (mDay < 10) {
                StringBuffer().append(mYear).append("/0").append(mMonth + 1).append("/0").append(mDay).toString()
            } else
                StringBuffer().append(mYear).append("/0").append(mMonth + 1).append("/").append(mDay).toString()
        } else {
            if (mDay < 10) {
                StringBuffer().append(mYear).append("/").append(mMonth + 1).append("/0").append(mDay).toString()
            } else
                StringBuffer().append(mYear).append("/").append(mMonth + 1).append("/").append(mDay).toString()

        }

        startcal = Calendar.getInstance()
        startcal.set(Calendar.YEAR, year)
        startcal.set(Calendar.MONTH, monthOfYear)
        startcal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        mDateAndTime = "" + days + getWeek(days)

        TimePickerDialog(activity, R.style.MyDatePickerDialogTheme, onTimeSetListener, 0, 0, true).show()
    }

    private val onTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

        mHour = if (hourOfDay == 0)
            "00"
        else if (hourOfDay.toString().length == 1) {
            String.format("0%s", hourOfDay.toString())
        } else hourOfDay.toString()

        mMinute = if (minute == 0)
            "00"
        else if (minute.toString().length == 1) {
            String.format("0%s", minute.toString())
        } else
            minute.toString()

        startcal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        startcal.set(Calendar.MINUTE, minute)

        mRealTime = SimpleDateFormat("yyyy/MM/dd HH:mm").format(Date(startcal.timeInMillis))
        Logger.d(mRealTime)
        mDateAndTime += " $mHour:$mMinute"
        tempString = mDateAndTime!!.substring(2, mDateAndTime!!.length)
        mTimeTV.text = tempString
    }

//    private val onTimeSetListener = TimePickerDialog.OnTimeSetListener()

    private fun timeFormat(): String {
        var HOUR = TimeUtils.getNowString().split(" ")[1].split(":")[0]
        val DATE = TimeUtils.getNowString().split(" ")[0].replace("-", "/")
        val YEAR = DATE.split("/")[0].substring(2, DATE.split("/")[0].length)
        val MONTH = DATE.split("/")[1]
        val DAY = DATE.split("/")[2]
        val WEEK = getWeek(DATE)
        HOUR = if (HOUR != "23")
            (HOUR.toInt() + 1).toString()
        else
            "00"
        return "$YEAR/$MONTH/$DAY$WEEK $HOUR:00"
    }

    private fun timeFormat2(): String {
        var HOUR = TimeUtils.getNowString().split(" ")[1].split(":")[0]
        val DATE = TimeUtils.getNowString().split(" ")[0].replace("-", "/")
        val YEAR = DATE.split("/")[0].substring(2, DATE.split("/")[0].length)
        val MONTH = DATE.split("/")[1]
        val DAY = DATE.split("/")[2]
        HOUR = if (HOUR != "23")
            (HOUR.toInt() + 1).toString()
        else
            "00"
        return "20$YEAR-$MONTH-$DAY $HOUR:00"
    }

    private var mReportTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            if (s.length > MAX_NUM)
                s.delete(MAX_NUM, s.length)
            val num = MAX_NUM - s.length
            mCountsTV.text = num.toString() + "/210"
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getWeek(time: String): String {
        var Week = ""
        val format = SimpleDateFormat("yyyy/MM/dd")
        val c = Calendar.getInstance()
        try {
            c.time = format.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val wek = c.get(Calendar.DAY_OF_WEEK)

        if (wek == 1) {
            Week += "周日"
        }
        if (wek == 2) {
            Week += "周一"
        }
        if (wek == 3) {
            Week += "周二"
        }
        if (wek == 4) {
            Week += "周三"
        }
        if (wek == 5) {
            Week += "周四"
        }
        if (wek == 6) {
            Week += "周五"
        }
        if (wek == 7) {
            Week += "周六"
        }
        return Week
    }

    override fun onBackPressedSupport(): Boolean {
        KeyboardUtils.hideSoftInput(activity)
        val bundle = Bundle()
        bundle.putString("COMMENT", "")
        bundle.putString("TIME", "")
        bundle.putString("REPEAT", "")
        bundle.putString("INDEX", "")
        bundle.putString("FIRST_IN", "")
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()
    }
}
