package com.android.lixiang.cheshang.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.lixiang.cheshang.R;
import com.orhanobut.logger.Logger;

import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class MessageAdapter extends BaseAdapter {
    public static final int TYPE_TIME = 0;
    public static final int TYPE_MESSAGE = 1;
    private List<String> mTitleList;
    private List<String> mDetailList;
    private List<String> mTimeList;
    private List<String> mIsReadList;

    private LayoutInflater inflater;
    private Context context;
    private int selectedItem = -1;

    public MessageAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_TIME;
        } else {
            return TYPE_MESSAGE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public MessageAdapter(List<String> mTitleList, List<String> mDetailList, List<String> mTimeList, List<String> mIsReadList, Context context) {
        this.mTitleList = mTitleList;
        this.mDetailList = mDetailList;
        this.mTimeList = mTimeList;
        this.mIsReadList = mIsReadList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTitleList == null ? 0 : mTitleList.size();
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
        ViewHolder holder = null;
        ViewHolder2 holder2 = null;
        switch (getItemViewType(position)) {
            case TYPE_TIME:
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.item_message_time_list, parent, false);
                    holder.mMessageTimeTV = convertView.findViewById(R.id.mMessageTimeTV);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

//                if (position != mTitleList.size())
                    holder.mMessageTimeTV.setText(mTimeList.get(position));
                break;
            case TYPE_MESSAGE:
                if (convertView == null) {
                    holder2 = new ViewHolder2();
                    convertView = inflater.inflate(R.layout.item_message_list, parent, false);
                    holder2.mMessageTitleTV = convertView.findViewById(R.id.mMessageTitleTV);
                    holder2.mMessageDetailTV = convertView.findViewById(R.id.mMessageDetailTV);
                    holder2.mNewRL = convertView.findViewById(R.id.mNewRL);
                    holder2.mNewTV = convertView.findViewById(R.id.mNewTV);

                    convertView.setTag(holder2);
                } else {
                    holder2 = (ViewHolder2) convertView.getTag();
                }

                holder2.mMessageTitleTV.setText(mTitleList.get(position));
                holder2.mMessageDetailTV.setText(mDetailList.get(position));

                if(mIsReadList.get(position).equals("true")){
                    holder2.mNewRL.setVisibility(View.GONE);
                    holder2.mNewTV.setVisibility(View.GONE);
                }else{
                    holder2.mNewRL.setVisibility(View.VISIBLE);
                    holder2.mNewTV.setVisibility(View.VISIBLE);
                }
                break;
        }
        return convertView;
    }

    final class ViewHolder {
        AppCompatTextView mMessageTimeTV;

    }

    final class ViewHolder2 {
        AppCompatTextView mMessageTitleTV;
        AppCompatTextView mMessageDetailTV;
        TriangleLabelView mNewRL;
        AppCompatTextView mNewTV;
    }
}
