package com.android.lixiang.cheshang.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.lixiang.base.utils.view.DimenUtil;
import com.android.lixiang.cheshang.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class LeftListViewAdapter extends BaseAdapter {

    List<String> data;
    LayoutInflater inflater;
    Context context;
    private int size = 0;
    private int[] mSelectIndexs;
    private int selectItem = 0;

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
        notifyDataSetChanged();
    }


    public LeftListViewAdapter(Context context, int size) {
        this.context = context;
        data = new ArrayList<>();
        this.size = size;
        mSelectIndexs = new int[size];
        DimenUtil dimenUtil = new DimenUtil();
        int count = (dimenUtil.px2dip(context, dimenUtil.getScreenHeight(context)) - 94) / 44;
        for (int i = 0; i < size; i++) {
            int j = i + 1;
            data.add("" + j);
            mSelectIndexs[i] = -1;
        }
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
//        DimenUtil dimenUtil = new DimenUtil();
//        return (dimenUtil.px2dip(context, dimenUtil.getScreenHeight(context)) - 94) / 44;
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void select(int index) {
        if (mSelectIndexs[index] == -1) {
            mSelectIndexs[index] = 0;
//        } else if (mSelectIndexs[index] == 0) {
//            mSelectIndexs[index] = -1;
        }
    }

    public void unSelect(int index) {
        if (mSelectIndexs[index] == 0) {
            mSelectIndexs[index] = -1;
//        } else if (mSelectIndexs[index] == 0) {
//            mSelectIndexs[index] = -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_left_listview, parent, false);
            holder = new ViewHolder(convertView);
            holder.mRootRL = convertView.findViewById(R.id.mRootRL);
            holder.mTitleTV = convertView.findViewById(R.id.mTitleTV);
            holder.mCheckMarkIV = convertView.findViewById(R.id.mCheckMarkIV);
            holder.mCheckedView = convertView.findViewById(R.id.mCheckedView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTitleTV.setText(data.get(position));

        if (selectItem == position) {
            holder.mRootRL.setBackgroundColor(Color.WHITE);
            holder.mTitleTV.setTextColor(Color.parseColor("#F73E00"));
            holder.mCheckedView.setVisibility(View.VISIBLE);
        } else {
            holder.mRootRL.setBackgroundColor(Color.parseColor("#EAEAEA"));
            holder.mTitleTV.setTextColor(Color.parseColor("#5C5C5C"));
            holder.mCheckedView.setVisibility(View.INVISIBLE);
        }

        for (int ignored : mSelectIndexs) {
            if (mSelectIndexs[position] == 0) {
                holder.mCheckMarkIV.setVisibility(View.VISIBLE);
            } else {
                holder.mCheckMarkIV.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    public static class ViewHolder {

        RelativeLayout mRootRL;
        AppCompatTextView mTitleTV;
        AppCompatImageView mCheckMarkIV;
        View mCheckedView;

        public ViewHolder(View itemView) {
            mRootRL = (RelativeLayout) itemView.findViewById(R.id.mRootRL);
            mTitleTV = (AppCompatTextView) itemView.findViewById(R.id.mTitleTV);
            mCheckMarkIV = (AppCompatImageView) itemView.findViewById(R.id.mCheckMarkIV);
            mCheckedView = (View) itemView.findViewById(R.id.mCheckedView);
        }
    }


}

