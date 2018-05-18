package com.karim.cakes.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.karim.cakes.R;
import com.karim.cakes.model.Recipe;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(getString(R.string.recipe_details));
        int index = intent.getIntExtra(getString(R.string.step_details), 0);
        getSupportActionBar().setTitle(recipe.getName());

        if (savedInstanceState == null) {
            CookingStepFragment cookingStepFragment = new CookingStepFragment();
            cookingStepFragment.setRecipe(recipe);
            cookingStepFragment.setIndex(index);

            final FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, cookingStepFragment)
                    .commit();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }
}
