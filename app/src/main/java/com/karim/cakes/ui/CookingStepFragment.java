package com.karim.cakes.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.karim.cakes.R;
import com.karim.cakes.model.Recipe;
import com.karim.cakes.model.Step;

import java.util.List;

public class CookingStepFragment extends Fragment {

    private List<Step> mSteps;
    private int mIndex;
    private Step mStep;
    private String mInstructionText = "";
    private String mUrl = "";
    private Recipe mRecipe;

    public CookingStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(getString(R.string.recipe_details));
            mIndex = savedInstanceState.getInt("Index");
        }

        View rootView = inflater.inflate(R.layout.fragment_cooking_step, container, false);
        final TextView cookingStepText = rootView.findViewById(R.id.fragment_text);
        Button nextButton = rootView.findViewById(R.id.next_button);
        Button previousButton = rootView.findViewById(R.id.previous_button);
        mSteps = mRecipe.getSteps();
        mStep = mSteps.get(mIndex);
        mInstructionText = mStep.getDescription();
        cookingStepText.setText(mInstructionText);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIndex < mSteps.size() - 1) {
                    mIndex++;
                    mStep = mSteps.get(mIndex);
                    mInstructionText = mStep.getDescription();
                    cookingStepText.setText(mInstructionText);
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIndex >= 1) {
                    mIndex--;
                    mStep = mSteps.get(mIndex);
                    mInstructionText = mStep.getDescription();
                    cookingStepText.setText(mInstructionText);
                }
            }
        });

        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }


    public void setIndex(int index) {
        mIndex = index;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.recipe_details), mRecipe);
        outState.putInt("Index", mIndex);
        outState.putString(getString(R.string.url), mUrl);
    }
}
