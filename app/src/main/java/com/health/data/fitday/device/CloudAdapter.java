package com.health.data.fitday.device;

import com.health.data.fitday.device.model.DialBean;
import com.sinophy.smartbracelet.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CloudAdapter extends RecyclerView.Adapter<CloudAdapter.ViewHolder> {
    private static final String TAG = "CloudAdapter";
    View.OnClickListener mOnClickListener;
    private List<DialBean> list;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivDial;
        private final TextView tvName;

        public ViewHolder(View v) {
            super(v);
            ivDial = (ImageView) v.findViewById(R.id.iv_dial);
            tvName = (TextView) v.findViewById(R.id.tv_dial_name);
        }

        public ImageView getImageView() {
            return ivDial;
        }
        public TextView getTvName() {
            return tvName;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param list String[] containing the data to populate views to be used by RecyclerView.
     */
    public CloudAdapter(List<DialBean> list) {
        this.list = list;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cloud_dial, viewGroup, false);
        v.setOnClickListener(mOnClickListener);
        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.itemView.setTag(position);
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        DialBean bean = list.get(position);
        viewHolder.getTvName().setText(bean.getDialName());
        viewHolder.getImageView().setImageResource(bean.getImage());
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }

    public View.OnClickListener getmOnClickListener() {
        return mOnClickListener;
    }

    public void setmOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}

