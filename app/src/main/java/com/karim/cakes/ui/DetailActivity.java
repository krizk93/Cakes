package com.karim.cakes.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.karim.cakes.R;
import com.karim.cakes.model.Recipe;
import com.karim.cakes.model.Step;

public class DetailActivity extends AppCompatActivity implements RecipeDetailsFragment.onRecipeClickListener {

    Recipe recipe;
    boolean mTwoPane;
    String url = "";
    String instructionText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(getString(R.string.recipe_details));
        if (findViewById(R.id.landscape_layout) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                instructionText = recipe.getSteps().get(0).getDescription();
                CookingStepFragment cookingStepFragment = new CookingStepFragment();
                cookingStepFragment.setSteps(recipe.getSteps());
                cookingStepFragment.setIndex(0);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, cookingStepFragment)
                        .commit();
            } else {
                instructionText = savedInstanceState.getString(getString(R.string.instruction_text));
            }


        } else mTwoPane = false;
    }

    @Override
    public Recipe onRecipeSelected() {
        if (recipe == null) {
            Intent intent = getIntent();
            recipe = intent.getParcelableExtra(getString(R.string.recipe_details));
            getSupportActionBar().setTitle(recipe.getName());
        }
        return recipe;
    }

    @Override
    public void onStepSelected(int index) {
        //String url = step.getVideoURL();
        //Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        //Step step = recipe.getSteps().get(index);
        if (mTwoPane) {
            CookingStepFragment cookingStepFragment = new CookingStepFragment();
            cookingStepFragment.setSteps(recipe.getSteps());
            cookingStepFragment.setIndex(index);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, cookingStepFragment)
                    .commit();

        } else {
            Intent intent = new Intent(DetailActivity.this, StepDetailActivity.class);
            intent.putExtra(getString(R.string.recipe_details), recipe);
            intent.putExtra(getString(R.string.step_details), index);
            startActivity(intent);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.instruction_text), instructionText);
        outState.putString(getString(R.string.url), url);
    }
}
