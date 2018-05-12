package com.karim.cakes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karim.cakes.R;
import com.karim.cakes.model.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    Context mContext;
    List<Step> mDataset;

    public StepsAdapter(Context context, List<Step> dataset){
        mContext = context;
        mDataset = dataset;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_item,parent,false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        String shortDescriptionText = mDataset.get(position).getShortDescription();
        String longDescriptionText = mDataset.get(position).getDescription();
        holder.shortDescription.setText(shortDescriptionText);
        holder.longDescription.setText(longDescriptionText);
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder{

        TextView shortDescription;
        TextView longDescription;

        public StepsViewHolder(View itemView) {
            super(itemView);
            shortDescription = itemView.findViewById(R.id.tv_short_description);
            longDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
