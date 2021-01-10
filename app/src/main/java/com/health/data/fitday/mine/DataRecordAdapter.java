package com.health.data.fitday.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinophy.smartbracelet.R;

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

    @Override
    public int getCount() {
        return this.mList.size();
    }

    @Override
    public Object getItem(int paramInt) {
        return this.mList.get(paramInt);
    }

    @Override
    public long getItemId(int paramInt) {
        return paramInt;
    }

    @Override
    public int getItemViewType(int paramInt) {
        return ((DataRecordBean)getItem(paramInt)).type;
    }

    @Override
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        SectionViewHolder sectionViewHolder;
        ViewHolder viewHolder;
        DataRecordBean dataRecordBean = (DataRecordBean)getItem(paramInt);
        if (dataRecordBean.type == 1) {
            SectionViewHolder sectionViewHolder1;
            if (paramView == null) {
                sectionViewHolder = new SectionViewHolder();
                paramView = this.mLayoutInflater.inflate(R.layout.item_data_record_section, null);
                sectionViewHolder.keyTextView = (TextView)paramView.findViewById(R.id.tv_key);
                paramView.setTag(sectionViewHolder);
            } else {
                sectionViewHolder = (SectionViewHolder)paramView.getTag();
            }
            sectionViewHolder.keyTextView.setText(dataRecordBean.getKey());
            return paramView;
        }
        if (paramView == null) {
            viewHolder = new ViewHolder();
            paramView = this.mLayoutInflater.inflate(R.layout.item_data_record, null);
            viewHolder.iconImageView = (ImageView)paramView.findViewById(R.id.iv_icon);
            viewHolder.keyTextView = (TextView)paramView.findViewById(R.id.tv_key);
            viewHolder.valueTextView = (TextView)paramView.findViewById(R.id.tv_value);
            viewHolder.unitTextView = (TextView)paramView.findViewById(R.id.tv_unit);
            paramView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)paramView.getTag();
        }
        viewHolder.keyTextView.setText(dataRecordBean.getKey());
        viewHolder.valueTextView.setText(dataRecordBean.getValue());
        viewHolder.unitTextView.setText(dataRecordBean.getUnit());
        viewHolder.iconImageView.setImageResource(dataRecordBean.getIcon());
        return paramView;
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
