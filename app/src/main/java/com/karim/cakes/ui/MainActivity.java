package com.karim.cakes.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.karim.cakes.R;
import com.karim.cakes.Widget.CakesWidget;
import com.karim.cakes.adapters.RecipesAdapter;
import com.karim.cakes.api.Client;
import com.karim.cakes.api.Service;
import com.karim.cakes.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeOnClickListener {

    private Context mContext;
    TextView textView;
    RecyclerView mRecyclerView;
    RecipesAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        mRecyclerView = findViewById(R.id.recycler_view);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mLayoutManager = new GridLayoutManager(mContext, 2);
            } else {
                mLayoutManager = new GridLayoutManager(mContext, 3);
            }

        } else {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mLayoutManager = new GridLayoutManager(mContext, 1);
            } else {
                mLayoutManager = new GridLayoutManager(mContext, 2);
            }
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mContext = getApplicationContext();
        Service apiService = Client.getClient().create(Service.class);
        Call<List<Recipe>> call = apiService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                mAdapter = new RecipesAdapter(recipes, mContext, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("retrofit", t.toString());
            }
        });
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        SharedPreferences sharedPreferences = getSharedPreferences("RECIPE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CHOSEN_RECIPE", json);
        editor.apply();

        updateMyWidgets(mContext);

/*        Intent widgetUpdateIntent = new Intent(this, CakesWidget.class);
        widgetUpdateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        widgetUpdateIntent.putExtra(CakesWidget.WIDGET_ID_KEY, ids);
        sendBroadcast(widgetUpdateIntent);*/

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(getString(R.string.recipe_details), recipe);
        startActivity(intent);
    }

    public static void updateMyWidgets(Context context) {
        AppWidgetManager man = AppWidgetManager.getInstance(context);
        int[] ids = man.getAppWidgetIds(
                new ComponentName(context,CakesWidget.class));
        Intent updateIntent = new Intent();
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(CakesWidget.WIDGET_IDS_KEY, ids);
        context.sendBroadcast(updateIntent);
    }
}
