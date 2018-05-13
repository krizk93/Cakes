package com.karim.cakes.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.karim.cakes.R;
import com.karim.cakes.model.Step;

public class StepDetailActivity extends AppCompatActivity {

    String url = "";
    String instructionText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Intent intent = getIntent();
        Step step = intent.getParcelableExtra(getString(R.string.step_details));
        getSupportActionBar().setTitle(step.getShortDescription());
        url = step.getVideoURL();
        instructionText = step.getDescription();

        CookingStepFragment cookingStepFragment = new CookingStepFragment();
        cookingStepFragment.setInstructionText(instructionText);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, cookingStepFragment)
                .commit();
    }
}
