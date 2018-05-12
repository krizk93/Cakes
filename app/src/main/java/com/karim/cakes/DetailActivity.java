package com.karim.cakes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.karim.cakes.model.Ingredient;
import com.karim.cakes.model.Recipe;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements RecipeDetailsFragment.onRecipeClickListener {

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public Recipe onRecipeSelected() {
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(getString(R.string.recipe_details));
        if (recipe == null) return null;
        return recipe;
    }
}
