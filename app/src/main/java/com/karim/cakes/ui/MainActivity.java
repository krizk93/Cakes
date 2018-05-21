package com.karim.cakes.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.karim.cakes.R;
import com.karim.cakes.Widget.CakesWidget;
import com.karim.cakes.adapters.RecipesAdapter;
import com.karim.cakes.api.Client;
import com.karim.cakes.api.Service;
import com.karim.cakes.databinding.ActivityMainBinding;
import com.karim.cakes.idlingResource.SimpleIdlingResource;
import com.karim.cakes.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipeOnClickListener {

    private Context mContext;
    RecipesAdapter mAdapter;
    ActivityMainBinding mainBinding;
    RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    private IdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = getApplicationContext();
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
        mainBinding.recipesRecyclerView.setLayoutManager(mLayoutManager);
        mainBinding.loadingTextview.setVisibility(View.VISIBLE);
        mainBinding.progressBar.setVisibility(View.VISIBLE);
        getmIdlingResource();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    public void loadData(){
        Service apiService = Client.getClient().create(Service.class);
        Call<List<Recipe>> call = apiService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                mAdapter = new RecipesAdapter(recipes, mContext, MainActivity.this);
                mainBinding.recipesRecyclerView.setAdapter(mAdapter);
                mainBinding.loadingTextview.setVisibility(View.INVISIBLE);
                mainBinding.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("retrofit", t.toString());
                mainBinding.progressBar.setVisibility(View.INVISIBLE);
                mainBinding.loadingTextview.setText(getString(R.string.error));
            }
        });
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_recipe), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.chosen_recipe), json);
        editor.apply();

        updateMyWidgets(mContext);

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(getString(R.string.recipe_details), recipe);
        startActivity(intent);
    }

    public static void updateMyWidgets(Context context) {
        AppWidgetManager man = AppWidgetManager.getInstance(context);
        int[] ids = man.getAppWidgetIds(
                new ComponentName(context, CakesWidget.class));
        Intent updateIntent = new Intent();
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(CakesWidget.WIDGET_IDS_KEY, ids);
        context.sendBroadcast(updateIntent);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getmIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new CountingIdlingResource("Retrofit Calls");
        }
        return mIdlingResource;
    }
}
