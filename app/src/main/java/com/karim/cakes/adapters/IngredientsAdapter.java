package com.karim.cakes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karim.cakes.R;
import com.karim.cakes.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private List<Ingredient> mDataset;
    private Context mContext;

    public IngredientsAdapter(Context context, List<Ingredient> dataset) {
        mContext = context;
        mDataset = dataset;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        String ingredient = mDataset.get(position).getIngredient();
        String quantity = mDataset.get(position).getQuantity() + " " + mDataset.get(position).getMeasure();
        holder.mIngredient.setText(ingredient);
        holder.mQuantity.setText(quantity);
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.size();
    }


    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView mIngredient;
        TextView mQuantity;

        IngredientsViewHolder(View itemView) {
            super(itemView);
            mIngredient = itemView.findViewById(R.id.text_ingredient);
            mQuantity = itemView.findViewById(R.id.text_quantity);
        }
    }
}
