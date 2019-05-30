package com.android.lixiang.cheshang.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.lixiang.cheshang.R;
import com.android.lixiang.cheshang.presenter.data.bean.GetAllAttendanceByHrAccountBean;
import com.android.lixiang.cheshang.ui.fragment.CheckFragment;
import com.android.lixiang.cheshang.ui.fragment.ConfirmCheckFragment;
import com.android.lixiang.cheshang.ui.fragment.IndexFragment;
import com.android.lixiang.cheshang.util.ClickProxy;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckNewAdapter extends BaseAdapter {
    private List<String> mTitleList;
    private List<String> mDetailList;
    private LayoutInflater inflater;
    private Context context;
    private int selectedItem = -1;

    private List<String> timeList = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();
    private List<String> stateList = new ArrayList<>();
    private List<String> lateList = new ArrayList<>();
    private List<String> failList = new ArrayList<>();
    private List<String> createTimeList = new ArrayList<>();
    private List<String> poiList = new ArrayList<>();
    private String[] attendance_flag = {"进店", "出店"};
    private String[] time_flag = {"正常", "迟到", "早退"};
    private String[] position_flag = {"失败", "成功"};
    private String[] reason = {"其他", "开会", "验车", "送单"};
    private IndexFragment checkFragment = null;

    public CheckNewAdapter(List<GetAllAttendanceByHrAccountBean.DataBean> list, Context context, IndexFragment checkFragment) {
        this.context = context;
        this.checkFragment = checkFragment;
        this.inflater = LayoutInflater.from(context);
        for (int i = 0; i < list.size(); i++) {
            createTimeList.add(list.get(i).getCreateTime());
            timeList.add(list.get(i).getCreateTime().split(" ")[1].substring(0, 5));
            String temp = list.get(i).getCreateTime().split(" ")[0].replace("-", "年");
            dateList.add(temp.substring(0, 7) + "月" + temp.substring(8, 10) + "日");
            stateList.add(attendance_flag[list.get(i).getAttendanceFlag()]);
            int index = list.get(i).getTimeFlag();
            if (index == -1)
                index = 0;
            lateList.add(time_flag[index]);
//            failList.add(position_flag[list.get(i).getPositionFlag()]);
            if (list.get(i).getReason() != -1)
                failList.add(position_flag[list.get(i).getPositionFlag()] + "：" + reason[list.get(i).getReason()]);
            else
                failList.add(position_flag[list.get(i).getPositionFlag()]);

            poiList.add(list.get(i).getPosition());
        }
    }

    public CheckNewAdapter(List<String> mTitleList, List<String> mDetailList, Context context) {
        this.mTitleList = mTitleList;
        this.mDetailList = mDetailList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return timeList == null ? 0 : timeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
//        View view = inflater.inflate(R.layout.item_check_listview, null);
                ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_check_listview, null);
            holder.mTimeTV = convertView.findViewById(R.id.mTimeTV);
            holder.mDateTV = convertView.findViewById(R.id.mDateTV);
            holder.mStateTV = convertView.findViewById(R.id.mStateTV);
            holder.mLateTV = convertView.findViewById(R.id.mLateTV);
            holder.mUpdateTV = convertView.findViewById(R.id.mUpdate);
            holder.mPoiTV = convertView.findViewById(R.id.mPoiTV);
            holder.mfailTV = convertView.findViewById(R.id.mFailTV);

            holder.mBtn = convertView.findViewById(R.id.mBtn);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTimeTV.setText(timeList.get(position));
        holder.mDateTV.setText(dateList.get(position));
        holder.mStateTV.setText(stateList.get(position));
        holder.mLateTV.setText(lateList.get(position));
        holder.mPoiTV.setText(poiList.get(position));
        holder.mfailTV.setText(failList.get(position));
        holder.mBtn.setOnClickListener(new ClickProxy(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmCheckFragment confirmCheckFragment = new ConfirmCheckFragment().newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("TAG", "UPDATE");
                bundle.putString("FROM", "");
                if (stateList.get(position).equals("进店")) {
                    bundle.putString("FLAG", "0");
                } else
                    bundle.putString("FLAG", "1");
                bundle.putString("CREATETIME", createTimeList.get(position));
                confirmCheckFragment.setArguments(bundle);
                checkFragment.start(confirmCheckFragment);
            }
        }));

        if (position == timeList.size() - 1) {
            holder.mUpdateTV.setVisibility(View.VISIBLE);
        } else {
            holder.mUpdateTV.setVisibility(View.GONE);
        }
        if (lateList.get(position).equals("正常")) {
            holder.mLateTV.setVisibility(View.GONE);
        } else {
            holder.mLateTV.setVisibility(View.VISIBLE);
        }

        if (failList.get(position).equals("成功")) {
            holder.mfailTV.setVisibility(View.GONE);
        } else {
            holder.mfailTV.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    final class ViewHolder {
        AppCompatTextView mTimeTV;
        AppCompatTextView mDateTV;
        AppCompatTextView mStateTV;
        AppCompatTextView mLateTV;
        AppCompatTextView mfailTV;

        AppCompatTextView mPoiTV;
        AppCompatTextView mUpdateTV;

        AppCompatButton mBtn;
    }
}
