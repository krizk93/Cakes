package com.karim.cakes.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.karim.cakes.R;
import com.karim.cakes.model.Recipe;
import com.karim.cakes.model.Step;

import java.util.List;

public class StepDetailActivity extends AppCompatActivity {

    private int mIndex;
    String url = "";
    String instructionText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(getString(R.string.recipe_details));
        mIndex = intent.getIntExtra(getString(R.string.step_details), 0);
        final List<Step> steps = recipe.getSteps();
        getSupportActionBar().setTitle(recipe.getName());
        //url = step.getVideoURL();

        if (savedInstanceState == null) {
            CookingStepFragment cookingStepFragment = new CookingStepFragment();
            cookingStepFragment.setRecipe(recipe);
            cookingStepFragment.setIndex(mIndex);

            final FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, cookingStepFragment)
                    .commit();
        }
/*        Button nextButton = findViewById(R.id.next_button);
        Button previousButton = findViewById(R.id.previous_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIndex < steps.size() - 1) {
                    mIndex++;
                    CookingStepFragment newCookingStepFragment = new CookingStepFragment();
                    newCookingStepFragment.setStep(steps.get(mIndex));
                    //newCookingStepFragment.setSteps(steps);
                    //newCookingStepFragment.setIndex(mIndex);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, newCookingStepFragment)
                            .commit();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIndex >= 1) {
                    mIndex--;
                    CookingStepFragment newCookingStepFragment = new CookingStepFragment();
                    newCookingStepFragment.setStep(steps.get(mIndex));
                    //newCookingStepFragment.setSteps(steps);
                    //newCookingStepFragment.setIndex(mIndex);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, newCookingStepFragment)
                            .commit();
                }
            }
        });*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.instruction_text), instructionText);
        outState.putString(getString(R.string.url), url);
    }

}
