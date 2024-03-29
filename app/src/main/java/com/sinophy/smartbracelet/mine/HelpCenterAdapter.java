package com.sinophy.smartbracelet.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinophy.smartbracelet.R;

import java.util.List;

class HelpCenterAdapter extends BaseAdapter {
    public Context mContext;

    public LayoutInflater mLayoutInflater;

    public List<HelpCenterBean> mList;

    public HelpCenterAdapter(List<HelpCenterBean> paramList, Context paramContext) {
        this.mList = paramList;
        this.mContext = paramContext;
        this.mLayoutInflater = LayoutInflater.from(paramContext);
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int paramInt) {
        return this.mList.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        ViewHolder viewHolder;
        if (paramView == null) {
            viewHolder = new ViewHolder();
            paramView = this.mLayoutInflater.inflate(R.layout.item_help_center, null);
            viewHolder.titleTextView = (TextView)paramView.findViewById(R.id.tv_question);
            paramView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)paramView.getTag();
        }
        HelpCenterBean helpCenterBean = this.mList.get(paramInt);
        viewHolder.titleTextView.setText(helpCenterBean.getTitle());
        return paramView;
    }

    private static class ViewHolder {
        public TextView titleTextView;

        private ViewHolder() {}
    }
}
