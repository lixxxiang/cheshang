package com.android.lixiang.cheshang.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.lixiang.cheshang.R;

import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class MissionListAdapter extends BaseAdapter {
    private List<String> mTitleList;
    private List<String> mDetailList;
    private List<String> mTagList;
    private LayoutInflater inflater;
    private Context context;
    private int selectedItem = -1;

    public MissionListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public MissionListAdapter(Context context, List<String> mTitleList, List<String> mDetailList, List<String> mTagList) {
        this.mTitleList = mTitleList;
        this.mDetailList = mDetailList;
        this.mTagList = mTagList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTitleList.size();
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_mission_list, null);
            holder.mMissionTitleTV = convertView.findViewById(R.id.mMissionTitleTV);
            holder.mMissionDetailTV = convertView.findViewById(R.id.mMissionDetailTV);
            holder.mTextInImageTV = convertView.findViewById(R.id.mTextInImageTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mMissionTitleTV.setText(mTitleList.get(position));
        holder.mMissionDetailTV.setText(mDetailList.get(position));
        String string = mTitleList.get(position);
        holder.mTextInImageTV.setText(string.substring(0, 1));
        return convertView;
    }

    final class ViewHolder {
        AppCompatTextView mMissionTitleTV;
        AppCompatTextView mMissionDetailTV;
        AppCompatTextView mTextInImageTV;
    }
}
