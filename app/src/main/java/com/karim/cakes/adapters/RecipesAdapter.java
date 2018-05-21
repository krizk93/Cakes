package com.karim.cakes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karim.cakes.R;
import com.karim.cakes.model.Recipe;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private List<Recipe> mDataset;
    private Context mContext;
    private final RecipeOnClickListener mOnClickListener;

    public interface RecipeOnClickListener {
        void onItemClick(Recipe recipe);
    }

    public RecipesAdapter(List<Recipe> dataset, Context context, RecipeOnClickListener listener) {
        mDataset = dataset;
        mContext = context;
        mOnClickListener = listener;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        String name = mDataset.get(position).getName();
        holder.mRecipeName.setText(name);
        switch (name) {
            case "Nutella Pie":
                holder.mImageView.setImageResource(R.drawable.nutella_pie);
                break;
            case "Brownies":
                holder.mImageView.setImageResource(R.drawable.brownies);
                break;
            case "Yellow Cake":
                holder.mImageView.setImageResource(R.drawable.yellow_cake);
                break;
            case "Cheesecake":
                holder.mImageView.setImageResource(R.drawable.cheesecake);
                break;
            default:
                holder.mImageView.setImageResource(R.drawable.ic_broken_image_black_24dp);
                break;
        }
        holder.mServingCount.setText("Servings: " + mDataset.get(position).getServings());
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.size();
    }

    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageView;
        TextView mRecipeName;
        TextView mServingCount;

        RecipesViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.recipe_image);
            mRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            mServingCount = itemView.findViewById(R.id.tv_servings);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onItemClick(mDataset.get(clickedPosition));
        }
    }
}
