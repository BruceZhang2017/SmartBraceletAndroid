package com.health.data.fitday.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.halfbit.pinnedsection.PinnedSectionListView;
import java.util.List;

class DataRecordAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    public Context mContext;

    public LayoutInflater mLayoutInflater;

    public List<DataRecordBean> mList;

    public DataRecordAdapter(List<DataRecordBean> paramList, Context paramContext) {
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

    public int getItemViewType(int paramInt) {
        return ((DataRecordBean)getItem(paramInt)).type;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        SectionViewHolder sectionViewHolder;
        ViewHolder viewHolder1;
        ViewHolder viewHolder2;
        DataRecordBean dataRecordBean = (DataRecordBean)getItem(paramInt);
        if (dataRecordBean.type == 1) {
            SectionViewHolder sectionViewHolder1;
            if (paramView == null) {
                sectionViewHolder = new SectionViewHolder();
                View view = this.mLayoutInflater.inflate(2131427423, null);
                sectionViewHolder.keyTextView = (TextView)view.findViewById(2131231321);
                view.setTag(sectionViewHolder);
            } else {
                SectionViewHolder sectionViewHolder2 = (SectionViewHolder)sectionViewHolder.getTag();
                sectionViewHolder1 = sectionViewHolder;
                sectionViewHolder = sectionViewHolder2;
            }
            sectionViewHolder.keyTextView.setText(dataRecordBean.getKey());
            return (View)sectionViewHolder1;
        }
        if (sectionViewHolder == null) {
            viewHolder1 = new ViewHolder();
            View view = this.mLayoutInflater.inflate(2131427422, null);
            viewHolder1.iconImageView = (ImageView)view.findViewById(2131231026);
            viewHolder1.keyTextView = (TextView)view.findViewById(2131231321);
            viewHolder1.valueTextView = (TextView)view.findViewById(2131231348);
            viewHolder1.unitTextView = (TextView)view.findViewById(2131231346);
            view.setTag(viewHolder1);
        } else {
            ViewHolder viewHolder = (ViewHolder)viewHolder1.getTag();
            viewHolder2 = viewHolder1;
            viewHolder1 = viewHolder;
        }
        viewHolder1.keyTextView.setText(dataRecordBean.getKey());
        viewHolder1.valueTextView.setText(dataRecordBean.getValue());
        viewHolder1.unitTextView.setText(dataRecordBean.getUnit());
        viewHolder1.iconImageView.setImageResource(dataRecordBean.getIcon());
        return (View)viewHolder2;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public boolean isItemViewTypePinned(int paramInt) {
        return false;
    }

    private static class SectionViewHolder {
        public TextView keyTextView;

        private SectionViewHolder() {}
    }

    private static class ViewHolder {
        public ImageView iconImageView;

        public TextView keyTextView;

        public TextView unitTextView;

        public TextView valueTextView;

        private ViewHolder() {}
    }
}
