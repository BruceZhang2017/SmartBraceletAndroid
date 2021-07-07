package com.health.data.fitday.main.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinophy.smartbracelet.R;

import org.w3c.dom.Text;

import java.util.List;

public class HealthAdapter extends BaseAdapter {
    View.OnClickListener mOnClickListener;
    public List<HealthBean> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_health, null);
        }
        ImageView ivIcon = (ImageView)convertView.findViewById(R.id.iv_icon);
        ivIcon.setImageResource(list.get(position).imageResource);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
        tvTitle.setText(list.get(position).getTitle());
        TextView tvAuthor = (TextView)convertView.findViewById(R.id.tv_author);
        tvAuthor.setText(list.get(position).getAuthor());
        TextView tvDate = (TextView)convertView.findViewById(R.id.tv_date);
        tvDate.setText(list.get(position).getDatetime());
        convertView.setOnClickListener(mOnClickListener);
        return convertView;
    }

    public View.OnClickListener getmOnClickListener() {
        return mOnClickListener;
    }

    public void setmOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}
