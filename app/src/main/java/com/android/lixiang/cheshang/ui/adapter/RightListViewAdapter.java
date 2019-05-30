package com.android.lixiang.cheshang.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.lixiang.cheshang.R;

public class RightListViewAdapter extends BaseAdapter {

    String data;
    LayoutInflater inflater;
    Context context;


    public RightListViewAdapter(Context context, String data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (data != null) {
            this.data = data;
        } else {
            this.data = "";
        }
    }

    public void addRes(String data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public String getItem(int position) {
        return data;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_right_listview, parent, false);
            convertView.getLayoutParams().height = parent.getHeight();
            convertView.requestLayout();
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTitle.setText("这是一段问题描述，由web管理端模板生成，经由后端发布给APP端进行展示？");
        return convertView;
    }

    public static class ViewHolder {
        TextView mTitle;

        public ViewHolder(View itemView) {
            mTitle = (TextView) itemView.findViewById(R.id.mQuestionTV);
        }
    }
}
