package com.android.lixiang.cheshang.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.android.lixiang.base.utils.view.DimenUtil;
import com.android.lixiang.cheshang.R;
import com.android.lixiang.cheshang.presenter.data.greenDao.Record;
import com.android.lixiang.cheshang.ui.fragment.WorkRecordFragment;
import com.blankj.utilcode.util.TimeUtils;
import com.orhanobut.logger.Logger;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkRecordAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private Context context;
    private int selectedItem = -1;
    private ArrayList<HashMap<String, String>> list;
    private int count = 0;
    private List<String> check;
    private int sizeAfter = 0;
    private boolean delete = false;
    private boolean addOrDeleteFlag = true;
    private int[] mSelectIndexs;
    private boolean isEdit = false;
    private List<Record> mRecordList = new ArrayList<>();
    private WorkRecordFragment fragment;
    private ArrayList<String> mDetailList = new ArrayList<>();
    private ArrayList<String> mTimeList = new ArrayList<>();
    private ArrayList<String> mRemindList = new ArrayList<>();
    public static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";
    private int flag = 0;
    private int todayCount = 0;
    private boolean[] isTodayFlag;

    public WorkRecordAdapter(List<Record> mRecordList, Context context, WorkRecordFragment fragment) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mRecordList = mRecordList;
        this.fragment = fragment;
        mSelectIndexs = new int[mRecordList.size()];
        isTodayFlag = new boolean[mRecordList.size()];

        for (int i = 0; i < mRecordList.size(); i++) {
            mDetailList.add(mRecordList.get(i).getDetail());
            mTimeList.add(mRecordList.get(i).getTime());
            mRemindList.add(mRecordList.get(i).getRemind());
            mSelectIndexs[i] = -1;
            isTodayFlag[i] = false;

        }
    }

    public void isEdit() {
        isEdit = true;
    }

    public void isNotEdit() {
        isEdit = false;
    }

    @Override
    public int getCount() {
        DimenUtil dimenUtil = new DimenUtil();
        Logger.d("DD" + (dimenUtil.px2dip(context, dimenUtil.getScreenHeight(context)) - 130) / 50);
        Logger.d(mTimeList.size());
        if ((dimenUtil.px2dip(context, dimenUtil.getScreenHeight(context)) - 130) / 50 > mTimeList.size())
            return (dimenUtil.px2dip(context, dimenUtil.getScreenHeight(context)) - 130) / 50;
        else
            return mTimeList.size() + 1;
//        return list.size() == 0 ? 1 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setCounts(int count) {
        this.count = count;
        delete = false;
//        sizeAfter = count;
    }

    public void setCheck(ArrayList<HashMap<String, String>> list) {
        check = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("flag").equals("true")) {
                check.add("" + i);
            }
        }
        delete = false;
    }

    public void setUnCheck(ArrayList<HashMap<String, String>> list) {
        check = null;
        delete = false;
        addOrDeleteFlag = true;
    }


    public void deleteItem(ArrayList<HashMap<String, String>> list) {
        delete = true;
        addOrDeleteFlag = false;
        sizeAfter = 0;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).get("content").equals(""))
                sizeAfter++;
        }
    }

    public void select(int index) {
        if (mSelectIndexs[index] == -1) {
            mSelectIndexs[index] = 0;
        } else if (mSelectIndexs[index] == 0) {
            mSelectIndexs[index] = -1;
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_work_record_listview, null);
            holder.mContentTV = convertView.findViewById(R.id.mContentTV);
            holder.mContentTV2 = convertView.findViewById(R.id.mContentTV2);
            holder.mTimeTV = convertView.findViewById(R.id.mTimeTV);
            holder.mTitleIV = convertView.findViewById(R.id.mTitleIV);
            holder.mRelativeLayout = convertView.findViewById(R.id.mHasDetailRL);
            holder.mExpireTV = convertView.findViewById(R.id.mExpireTV);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /**
         * 添加文字
         */

        if (position == count) {
            holder.mTitleIV.setVisibility(View.VISIBLE);
            holder.mTitleIV.setBackgroundResource(R.drawable.ic_add);
        } else if (position < count) {
            if (addOrDeleteFlag)
                holder.mTitleIV.setBackgroundResource(R.drawable.ic_checkbox_uncheck);
        }

        if (isEdit && position < mSelectIndexs.length) {
            for (int ignored : mSelectIndexs) {
                if (mSelectIndexs[position] == 0) {
                    holder.mTitleIV.setBackgroundResource(R.drawable.ic_checkmark);
                } else {
                    holder.mTitleIV.setBackgroundResource(R.drawable.ic_checkbox_uncheck);
                }
            }
        }


        if (delete) {
            if (position < sizeAfter) {
                holder.mTitleIV.setBackgroundResource(R.drawable.ic_checkbox_uncheck);
            } else if (position == sizeAfter) {
                holder.mTitleIV.setBackgroundResource(R.drawable.ic_add);
            } else {
                holder.mTitleIV.setVisibility(View.GONE);
            }
        }
        if (mDetailList.size() != 0 && position < mDetailList.size()) {
            if (mTimeList.get(position).equals("")) {
                holder.mRelativeLayout.setVisibility(View.GONE);
                holder.mContentTV2.setVisibility(View.VISIBLE);
                holder.mContentTV2.setText(mDetailList.get(position));
            } else {
                holder.mRelativeLayout.setVisibility(View.VISIBLE);
                holder.mContentTV2.setVisibility(View.GONE);
                holder.mContentTV.setText(mDetailList.get(position));
                holder.mTimeTV.setText(String.format("%s%s，%s", compareDates(reformatDate(mTimeList.get(position)), TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"))), reformatTime(mTimeList.get(position)), mRemindList.get(position)));

                flag = getDayLength(reformatDate(mTimeList.get(position)), TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));

                if (mRemindList.get(position).equals("永不")) {
                    if (flag > 0) {
                        /**
                         *      <---    expire
                         */
                        holder.mExpireTV.setVisibility(View.VISIBLE);
                    } else if (flag == 0) {
                        /**
                         *  today compare
                         */
                        if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                            isTodayFlag[position] = true;
                        } else {
                            holder.mExpireTV.setVisibility(View.VISIBLE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else {
                        /**
                         *   --> none
                         */
                        holder.mExpireTV.setVisibility(View.GONE);
                        holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                    }
                } else if (mRemindList.get(position).equals("每天")) {
                    if (flag > 0) {
                        /**
                         *      <---    compare
                         */
                        if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                            isTodayFlag[position] = true;

                        } else {
//                            holder.mExpireTV.setVisibility(View.VISIBLE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else if (flag == 0) {
                        /**
                         *  today compare
                         */
                        if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                            isTodayFlag[position] = true;

                        } else {
//                            holder.mExpireTV.setVisibility(View.VISIBLE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else {
                        /**
                         *   --> none
                         */
                        holder.mExpireTV.setVisibility(View.GONE);
                        holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                    }
                } else if (mRemindList.get(position).equals("每周")) {
                    if (flag > 0) {
                        /**
                         *      <---    compare
                         */
                        if (isTodayForWeek(reformatDate(mTimeList.get(position)))) {
                            if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                                holder.mExpireTV.setVisibility(View.GONE);
                                holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                                isTodayFlag[position] = true;

                            } else {
//                                holder.mExpireTV.setVisibility(View.VISIBLE);
                                holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                            }
                        } else {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else if (flag == 0) {
                        /**
                         *  today compare
                         */
                        if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                            isTodayFlag[position] = true;

                        } else {
//                            holder.mExpireTV.setVisibility(View.VISIBLE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else {
                        /**
                         *   --> none
                         */
                        holder.mExpireTV.setVisibility(View.GONE);
                        holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                    }
                } else if (mRemindList.get(position).equals("每两周")) {
                    if (flag > 0) {
                        /**
                         *      <---    compare
                         */
                        if (isTodayFor2Week(reformatDate(mTimeList.get(position)))) {
                            if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                                holder.mExpireTV.setVisibility(View.GONE);
                                holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                                isTodayFlag[position] = true;

                            } else {
//                                holder.mExpireTV.setVisibility(View.VISIBLE);
                                holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                            }
                        } else {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else if (flag == 0) {
                        /**
                         *  today compare
                         */
                        if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                            isTodayFlag[position] = true;

                        } else {
//                            holder.mExpireTV.setVisibility(View.VISIBLE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else {
                        /**
                         *   --> none
                         */
                        holder.mExpireTV.setVisibility(View.GONE);
                        holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                    }
                } else if (mRemindList.get(position).equals("每月")) {
                    if (flag > 0) {
                        /**
                         *      <---    compare
                         */
                        if (isTodayForMonth(reformatDate(mTimeList.get(position)))) {
                            if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                                holder.mExpireTV.setVisibility(View.GONE);
                                holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                                isTodayFlag[position] = true;

                            } else {
//                                holder.mExpireTV.setVisibility(View.VISIBLE);
                                holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                            }
                        } else {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else if (flag == 0) {
                        /**
                         *  today compare
                         */
                        if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                            isTodayFlag[position] = true;

                        } else {
//                            holder.mExpireTV.setVisibility(View.VISIBLE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else {
                        /**
                         *   --> none
                         */
                        holder.mExpireTV.setVisibility(View.GONE);
                        holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                    }
                } else if (mRemindList.get(position).equals("每年")) {
                    if (flag > 0) {
                        /**
                         *      <---    compare
                         */
                        if (isTodayForYear(reformatDate(mTimeList.get(position)))) {
                            if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                                holder.mExpireTV.setVisibility(View.GONE);
                                holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                                isTodayFlag[position] = true;

                            } else {
//                                holder.mExpireTV.setVisibility(View.VISIBLE);
                                holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                            }
                        } else {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else if (flag == 0) {
                        /**
                         *  today compare
                         */
                        if (compareTime(TimeUtils.getNowString(new SimpleDateFormat("HH:mm")), reformatTime2(mTimeList.get(position))) < 0) {
                            holder.mExpireTV.setVisibility(View.GONE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#F73E00"));
                            isTodayFlag[position] = true;

                        } else {
//                            holder.mExpireTV.setVisibility(View.VISIBLE);
                            holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                        }
                    } else {
                        /**
                         *   --> none
                         */
                        holder.mExpireTV.setVisibility(View.GONE);
                        holder.mTimeTV.setTextColor(Color.parseColor("#686868"));
                    }
                }
            }
        }


        /**
         * 添加图标
         */
        count = mDetailList.size();
        return convertView;
    }

    public boolean[] count() {
//        int count = 0;
//
//        for (int i = 0; i < isTodayFlag.length; i++) {
//            if (isTodayFlag[i]) {
//                count++;
//            }
//        }
        return isTodayFlag;
    }

    final class ViewHolder {
        AppCompatTextView mContentTV;
        AppCompatTextView mContentTV2;
        RelativeLayout mRelativeLayout;
        AppCompatTextView mTimeTV;
        AppCompatImageView mTitleIV;
        AppCompatTextView mExpireTV;

    }

    private String compareDates(String start_date, String end_date) {
        if (getDayLength(start_date, end_date) == 0)
            return "今天";
        else if (getDayLength(start_date, end_date) == 1)
            return "昨天";
        else if (getDayLength(start_date, end_date) == 2)
            return "前天";
        else if (getDayLength(start_date, end_date) == -1)
            return "明天";
        else if (getDayLength(start_date, end_date) == -2)
            return "后天";
        return start_date;
    }


    private int getDayLength(String start_date, String end_date) {

        Date fromDate = null; //开始日期
        try {
            fromDate = getStrToDate(start_date, DATE_FORMAT_NORMAL);
            Date toDate = getStrToDate(end_date, DATE_FORMAT_NORMAL); //结束日期
            long from = fromDate.getTime();
            long to = toDate.getTime();
            int day = (int) ((to - from) / (24 * 60 * 60 * 1000));
            return day;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }


    private Date getStrToDate(String date, String fomtter) throws ParseException {
        DateFormat df = new SimpleDateFormat(fomtter);
        return df.parse(date);
    }

    private String reformatDate(String date) {
//        int index = date.indexOf("周");
//        if (date.charAt(index - 2) == '/') {
//            date = date.substring(0, index - 1) + "0" + date.substring(index - 1, index + 1) + " " + date.substring(index + 1, date.length());
//        }
        String temp = date.substring(0, 8);
        temp = "20" + temp;
        return temp.replace("/", "-");
    }

    private String reformatTime(String date) {
        return date.substring(10, date.length());
    }

    private String reformatTime2(String date) {
        return date.substring(11, date.length());
    }

    private int compareTime(String startTimeStr, String endTimeStr) {

        Pattern p = Pattern.compile("^([0-2][0-9]):([0-5][0-9])"); //Regex is used to validate time format (HH:MM:SS)

        int hhS = 0;
        int mmS = 0;

        int hhE = 0;
        int mmE = 0;

        Matcher m = null;

        m = p.matcher(startTimeStr);
        if (m.matches()) {
            String hhStr = m.group(1);
            String mmStr = m.group(2);

            hhS = Integer.parseInt(hhStr);
            mmS = Integer.parseInt(mmStr);

        } else {
            System.out.println("Invalid start time");
            System.exit(0);

        }


        m = p.matcher(endTimeStr);
        if (m.matches()) {
            String hhStr = m.group(1);
            String mmStr = m.group(2);

            hhE = Integer.parseInt(hhStr);
            mmE = Integer.parseInt(mmStr);

        } else {
            System.out.println("Invalid End time");
            System.exit(0);

        }


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hhS); // Start hour
        cal.set(Calendar.MINUTE, mmS); // Start Mintue

        Time startTime = new Time(cal.getTime().getTime());
        // System.out.println("your time: "+sqlTime3);

        cal.set(Calendar.HOUR_OF_DAY, hhE); // End hour
        cal.set(Calendar.MINUTE, mmE); // End Mintue

        Time endTime = new Time(cal.getTime().getTime());

        if (startTime.equals(endTime)) {
            return 0;

        } else if (startTime.before(endTime)) {
            return -1;
        } else
            return 1;

    }

    private boolean isToday(String date) {
        return TimeUtils.isToday(date, new SimpleDateFormat("yyyy/MM/dd"));
    }

    private boolean isTodayForWeek(String date) {
        int i = differentDays(date, TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));
        if (i % 7 == 0) {
            return true;
        } else
            return false;
    }

    private boolean isTodayFor2Week(String date) {
        int i = differentDays(date, TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));
        if (i % 14 == 0) {
            return true;
        } else
            return false;
    }

    private boolean isTodayForMonth(String date) {
//    public static boolean isTodayForMonth(String date) {
        String s1 = date.substring(date.lastIndexOf("-") + 1, date.length());
        String s2 = TimeUtils.getNowString(new SimpleDateFormat("dd"));
        if (s1.equals(s2)) {
            return true;
        } else
            return false;
    }

    //    private boolean isTodayForYear(String date) {
    public static boolean isTodayForYear(String date) {
        String s1 = date.substring(date.indexOf("-") + 1, date.length());
        String s2 = TimeUtils.getNowString(new SimpleDateFormat("MM-dd"));
        if (s1.equals(s2)) {
            return true;
        } else
            return false;
    }


    public static void main(String[] args) throws ParseException {
//        System.out.println(isTodayForWeek("2018/11/20"));
        System.out.println(isTodayForYear("2017-11-13"));
        System.out.println(isTodayForYear("2019-11-13"));
        System.out.println(isTodayForYear("2018-11-13"));
        System.out.println(isTodayForYear("2018-12-07"));
//

    }

    private static int differentDays(String s1, String s2) {

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = null;
            date1 = format.parse(s1);
            Date date2 = format.parse(s2);

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            int day1 = cal1.get(Calendar.DAY_OF_YEAR);
            int day2 = cal2.get(Calendar.DAY_OF_YEAR);

            int year1 = cal1.get(Calendar.YEAR);
            int year2 = cal2.get(Calendar.YEAR);
            if (year1 != year2)   //同一年
            {
                int timeDistance = 0;
                for (int i = year1; i < year2; i++) {
                    if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                    {
                        timeDistance += 366;
                    } else    //不是闰年
                    {
                        timeDistance += 365;
                    }
                }

                return timeDistance + (day2 - day1);
            } else    //不同年
            {
                System.out.println("判断day2 - day1 : " + (day2 - day1));
                return day2 - day1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
