package com.android.lixiang.cheshang.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.base.utils.view.EditTextWithDeleteButton
import com.android.lixiang.cheshang.R
import com.android.lixiang.cheshang.presenter.data.bean.GetMissionListBean
import com.android.lixiang.cheshang.util.CheshangApplication
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_layout.*

class PlaceholderFragment : Fragment() {
    private var mTypeList: MutableList<Int>? = mutableListOf()
    private var mGetMissionListDataBean: GetMissionListBean.DataBean? = null
    private var mQuestionTV: AppCompatTextView? = null
    private var mQuestionTV2: AppCompatTextView? = null
    private var mQuestionTV3: AppCompatTextView? = null
    private var mSingleTV: AppCompatTextView? = null
    private var mMultiTV: AppCompatTextView? = null
    private var mRequired: AppCompatTextView? = null
    private var mNotRequired: AppCompatTextView? = null
    private var mRequired2: AppCompatTextView? = null
    private var mNotRequired2: AppCompatTextView? = null
    private var mRequired3: AppCompatTextView? = null
    private var mNotRequired3: AppCompatTextView? = null
    private var mDanweiTV: AppCompatTextView? = null

    private var mWenZiRL: RelativeLayout? = null
    private var mShuZiRL: RelativeLayout? = null
    private var mXuanZeRL: RelativeLayout? = null

    private var mWenZiET: EditTextWithDeleteButton? = null
    private var mShuZiET: EditTextWithDeleteButton? = null

    private var mOptionRL = arrayOfNulls<RelativeLayout>(100)
    private var mOptionTV = arrayOfNulls<AppCompatTextView>(100)
    private var mUnderLine2: View? = null
    private var mUnderLine3: View? = null

    private var mClickFlag: MutableList<Boolean>? = mutableListOf()
    private val ARG_SECTION_NUMBER = "section_number"
    private var ANSWERS: MutableMap<Int, String> = mutableMapOf()
    private var mQuestionIdList: MutableList<String>? = mutableListOf()
    private var index: Int? = 0
    private var mRequiredList: MutableList<Int>? = mutableListOf()


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    fun newInstance(sectionNumber: Int, data: GetMissionListBean.DataBean, size: Int, ANSWERS: MutableMap<Int, String>): PlaceholderFragment {
        val fragment = PlaceholderFragment()
        val args = Bundle()
        this.ANSWERS = ANSWERS
        args.putInt(ARG_SECTION_NUMBER, sectionNumber)
        args.putSerializable("DATA", data)
        args.putInt("SIZE", size)
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_layout, container, false)
        val mQuestionList: MutableList<String>? = mutableListOf()
        val mOptionSizeList: MutableList<Int>? = mutableListOf()
        val mOptionList: MutableList<String>? = mutableListOf()
        val mRequiredList: MutableList<Int>? = mutableListOf()

        mQuestionTV = rootView.findViewById(R.id.mQuestionTV)
        mQuestionTV2 = rootView.findViewById(R.id.mQuestionTV2)
        mQuestionTV3 = rootView.findViewById(R.id.mQuestionTV3)
        mDanweiTV = rootView.findViewById(R.id.mDanWeiTV)
        mSingleTV = rootView.findViewById(R.id.mSingleTV)
        mMultiTV = rootView.findViewById(R.id.mMultiTV)
        mRequired = rootView.findViewById(R.id.mRequiredTV)
        mNotRequired = rootView.findViewById(R.id.mNotRequiredTV)
        mRequired2 = rootView.findViewById(R.id.mRequiredTV2)
        mNotRequired2 = rootView.findViewById(R.id.mNotRequiredTV2)
        mRequired3 = rootView.findViewById(R.id.mRequiredTV3)
        mNotRequired3 = rootView.findViewById(R.id.mNotRequiredTV3)

        mWenZiET = rootView.findViewById(R.id.mWenZiET)
        mShuZiET = rootView.findViewById(R.id.mShuZiET)
        mUnderLine2 = rootView.findViewById(R.id.mUnderLine2)
        mUnderLine3 = rootView.findViewById(R.id.mUnderLine3)
        mWenZiET!!.addTextChangedListener(mWenZiTextWatcher)
        mShuZiET!!.addTextChangedListener(mShuZiTextWatcher)

        mWenZiET!!.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                mUnderLine2!!.setBackgroundColor(Color.parseColor("#F73E00"))
            } else
                mUnderLine2!!.setBackgroundColor(Color.parseColor("#5C5C5C"))
        }

        mShuZiET!!.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                mUnderLine3!!.setBackgroundColor(Color.parseColor("#F73E00"))
            } else
                mUnderLine3!!.setBackgroundColor(Color.parseColor("#5C5C5C"))
        }

        mWenZiRL = rootView.findViewById(R.id.mWenZiRL)
        mShuZiRL = rootView.findViewById(R.id.mShuZiRL)
        mXuanZeRL = rootView.findViewById(R.id.mXuanZeRL)

        mGetMissionListDataBean = arguments!!.getSerializable("DATA") as GetMissionListBean.DataBean

        for (i in 0 until mGetMissionListDataBean!!.questionList.size) {
            if (mGetMissionListDataBean!!.questionList[i].option != null) {
                mOptionSizeList!!.add(mGetMissionListDataBean!!.questionList[i].option.split(";").size)
                mOptionList!!.add(mGetMissionListDataBean!!.questionList[i].option)
            } else {
                mOptionSizeList!!.add(0)
                mOptionList!!.add("")
            }
            mRequiredList!!.add(mGetMissionListDataBean!!.questionList[i].required)

            this.mRequiredList = mRequiredList
            mQuestionList!!.add(mGetMissionListDataBean!!.questionList[i].question)
            mQuestionIdList!!.add("" + mGetMissionListDataBean!!.missionId + mGetMissionListDataBean!!.questionList[i].id)
            mTypeList!!.add(mGetMissionListDataBean!!.questionList[i].type)
        }

//        Logger.d(mRequiredList)
//        for (i in 0 until mRequiredList!!.size) {
//            if (mRequiredList[i] == 2) {
//                val answer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.load(mQuestionIdList!![arguments!!.getInt(ARG_SECTION_NUMBER)])
//                if (answer != null){
//                    answer.answer = ""
//                    (activity!!.application as CheshangApplication).getDaoSession().answerDao.update(answer)
//                    (parentFragment as MissionTodayFragment).optionClick2(arguments!!.getInt(ARG_SECTION_NUMBER))
//                }
//            } else {
//                (parentFragment as MissionTodayFragment).optionUnClick2(arguments!!.getInt(ARG_SECTION_NUMBER))
//            }
//        }

        for (i in 0 until arguments!!.getInt("SIZE")) {

            if (i == arguments!!.getInt(ARG_SECTION_NUMBER))
                when (mTypeList!![i]) {
                    1 -> {
                        val index = i
                        mQuestionTV!!.text = mQuestionList!![i]
                        mWenZiRL!!.visibility = View.GONE
                        mShuZiRL!!.visibility = View.GONE
                        mXuanZeRL!!.visibility = View.VISIBLE
                        setOptions(mOptionSizeList!![i], mXuanZeRL!!, mOptionList!![i], index, true, mRequiredList!![i])
                    }
                    2 -> {
                        val index = i
                        mQuestionTV!!.text = mQuestionList!![i]
                        mWenZiRL!!.visibility = View.GONE
                        mShuZiRL!!.visibility = View.GONE
                        mXuanZeRL!!.visibility = View.VISIBLE
                        setOptions(mOptionSizeList!![i], mXuanZeRL!!, mOptionList!![i], index, false, mRequiredList!![i])
                    }
                    3 -> {
                        mQuestionTV2!!.text = mQuestionList!![i]
                        mWenZiRL!!.visibility = View.VISIBLE
                        mShuZiRL!!.visibility = View.GONE
                        mXuanZeRL!!.visibility = View.GONE
                        if (mRequiredList!![i] == 1) {
                            mRequired2!!.visibility = View.VISIBLE
                            mNotRequired2!!.visibility = View.GONE

                        } else if (mRequiredList[i] == 2) {
                            mRequired2!!.visibility = View.GONE
                            mNotRequired2!!.visibility = View.VISIBLE
                        }

                    }
                    4 -> {
                        mQuestionTV3!!.text = mQuestionList!![i]
                        mWenZiRL!!.visibility = View.GONE
                        mShuZiRL!!.visibility = View.VISIBLE
                        mXuanZeRL!!.visibility = View.GONE
                        mDanweiTV!!.text = mOptionList!![i]
                        if (mRequiredList!![i] == 1) {
                            mRequired3!!.visibility = View.VISIBLE
                            mNotRequired3!!.visibility = View.GONE

                        } else if (mRequiredList[i] == 2) {
                            mRequired3!!.visibility = View.GONE
                            mNotRequired3!!.visibility = View.VISIBLE
                        }

                    }
                }
        }

        return rootView
    }

    private fun setOptions(size: Int, root: RelativeLayout, options: String, index: Int, isSingle: Boolean, required: Int) {
        if (isSingle) {
            mSingleTV!!.visibility = View.VISIBLE
            mMultiTV!!.visibility = View.INVISIBLE
        } else {
            mSingleTV!!.visibility = View.INVISIBLE
            mMultiTV!!.visibility = View.VISIBLE
        }

        if (required == 1) {
            mRequired!!.visibility = View.VISIBLE
            mNotRequired!!.visibility = View.INVISIBLE
        } else if (required == 2) {
            mRequired!!.visibility = View.INVISIBLE
            mNotRequired!!.visibility = View.VISIBLE
        }


        for (i in 0 until size) {
            mOptionRL[i] = RelativeLayout(activity)
            mOptionRL[i]!!.id = i + 1
            val mOptionRLParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DimenUtil().dip2px(context!!, 32F))
            mOptionRLParams.addRule(RelativeLayout.BELOW, R.id.mQuestionTV)
            mOptionRLParams.setMargins(DimenUtil().dip2px(context!!, 62F), DimenUtil().dip2px(context!!, (33 + 52 * i).toFloat()), DimenUtil().dip2px(context!!, 62F), 0)
            mOptionRL[i]!!.setBackgroundResource(R.drawable.round_relativelayout_100_gray_9b9b9b)
            mOptionRL[i]!!.layoutParams = mOptionRLParams
            mOptionRL[i]!!.isClickable = true
            mOptionRL[i]!!.isFocusable = true
            root.addView(mOptionRL[i], mOptionRLParams)


            mOptionTV[i] = AppCompatTextView(activity)
            mOptionTV[i]!!.id = i
            val mOptionTVParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            mOptionTVParams.addRule(RelativeLayout.CENTER_IN_PARENT, mOptionRL[i]!!.id)
            mOptionTV[i]!!.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
            mOptionTV[i]!!.setTextColor(Color.WHITE)
            mOptionRL[i]!!.addView(mOptionTV[i], mOptionTVParams)
        }

        val optionsSize = options.split(";").size
        for (i in 0 until optionsSize) {
            mOptionTV[i]!!.text = options.split(";")[i]
        }

        for (i in 0 until size) {
            mClickFlag!!.add(false)
            mOptionRL[i]!!.setOnClickListener {

                if (isSingle) {
                    /**
                     * single
                     */
                    mOptionRL[i]!!.setBackgroundResource(R.drawable.round_relativelayout_100_red)
                    for (j in 0 until size) {
                        if (j != i) {
                            mOptionRL[j]!!.setBackgroundResource(R.drawable.round_relativelayout_100_gray_9b9b9b)
                            val answer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.load(mQuestionIdList!![index])
                            answer.answer = options.split(";")[i]
                            (activity!!.application as CheshangApplication).getDaoSession().answerDao.update(answer)
                        }
                    }
                    (parentFragment as MissionTodayFragment).optionClick(arguments!!.getInt(ARG_SECTION_NUMBER))
                } else {
                    /**
                     * multi
                     */
                    if (!mClickFlag!![i]) {
                        mOptionRL[i]!!.setBackgroundResource(R.drawable.round_relativelayout_100_red)
                        mClickFlag!![i] = true
                        val answer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.load(mQuestionIdList!![index])
                        val tempAnswer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.load(mQuestionIdList!![index]).answer
                        if (tempAnswer != "")
                            answer.answer = tempAnswer + ";" + options.split(";")[i]
                        else
                            answer.answer = options.split(";")[i]
                        (activity!!.application as CheshangApplication).getDaoSession().answerDao.update(answer)

                    } else {
                        mOptionRL[i]!!.setBackgroundResource(R.drawable.round_relativelayout_100_gray_9b9b9b)
                        mClickFlag!![i] = false
                        val answer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.load(mQuestionIdList!![index])
                        var tempAnswer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.load(mQuestionIdList!![index]).answer
                        if (tempAnswer.contains(";")) {
                            val tail = tempAnswer.lastIndexOf(";")
                            Logger.d(tail)
                            tempAnswer = tempAnswer.substring(0, tail)
                            answer.answer = tempAnswer
                        } else
                            answer.answer = ""
                        (activity!!.application as CheshangApplication).getDaoSession().answerDao.update(answer)

                    }

                    if (mClickFlag!!.contains(true)) {
                        (parentFragment as MissionTodayFragment).optionClick(arguments!!.getInt(ARG_SECTION_NUMBER))
                    } else
                        (parentFragment as MissionTodayFragment).optionUnClick(arguments!!.getInt(ARG_SECTION_NUMBER))
                }
            }
        }
    }

    private var mWenZiTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            if (!mWenZiET!!.text!!.isEmpty()) {
                val answer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.load(mQuestionIdList!![arguments!!.getInt(ARG_SECTION_NUMBER)])
                answer.answer = mWenZiET!!.text.toString()
                (activity!!.application as CheshangApplication).getDaoSession().answerDao.update(answer)
                (parentFragment as MissionTodayFragment).optionClick(arguments!!.getInt(ARG_SECTION_NUMBER))
            } else {
//                if (mRequiredList!![arguments!!.getInt(ARG_SECTION_NUMBER)] == 1) {
//                    val answer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.load(mQuestionIdList!![arguments!!.getInt(ARG_SECTION_NUMBER)])
//                    answer.answer = ""
//                    (activity!!.application as CheshangApplication).getDaoSession().answerDao.update(answer)
//                    (parentFragment as MissionTodayFragment).optionClick(arguments!!.getInt(ARG_SECTION_NUMBER))
//                } else {
                (parentFragment as MissionTodayFragment).optionUnClick(arguments!!.getInt(ARG_SECTION_NUMBER))
//                }
            }
        }
    }

    private var mShuZiTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            if (!mShuZiET!!.text.toString().isEmpty()) {
                val answer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.load(mQuestionIdList!![arguments!!.getInt(ARG_SECTION_NUMBER)])
                answer.answer = mShuZiET!!.text.toString()
                (activity!!.application as CheshangApplication).getDaoSession().answerDao.update(answer)
                (parentFragment as MissionTodayFragment).optionClick(arguments!!.getInt(ARG_SECTION_NUMBER))
            } else {
//                if (mRequiredList!![arguments!!.getInt(ARG_SECTION_NUMBER)] == 1) {
//                    val answer = (activity!!.application as CheshangApplication).getDaoSession().answerDao.load(mQuestionIdList!![arguments!!.getInt(ARG_SECTION_NUMBER)])
//                    answer.answer = ""
//                    (activity!!.application as CheshangApplication).getDaoSession().answerDao.update(answer)
//                    (parentFragment as MissionTodayFragment).optionClick(arguments!!.getInt(ARG_SECTION_NUMBER))
//                } else
                (parentFragment as MissionTodayFragment).optionUnClick(arguments!!.getInt(ARG_SECTION_NUMBER))

            }
        }
    }
}