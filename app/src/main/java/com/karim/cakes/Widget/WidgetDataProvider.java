package com.karim.cakes.Widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.karim.cakes.model.Ingredient;
import com.karim.cakes.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetDataProvider";

    private List<String> mCollection = new ArrayList<>();
    private Context mContext;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollection.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, mCollection.get(i));
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
        mCollection.clear();
        /*for (int i = 1; i <= 10; i++) {
            mCollection.add("ListView item " + i);
        }*/
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("RECIPE", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("CHOSEN_RECIPE", "");
        if (!json.isEmpty()) {
            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(json, Recipe.class);
            List<Ingredient> ingredients = recipe.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                mCollection.add(ingredients.get(i).getIngredient() + " = " + ingredients.get(i).getQuantity() + " " + ingredients.get(i).getMeasure());
            }
        }
    }
}
