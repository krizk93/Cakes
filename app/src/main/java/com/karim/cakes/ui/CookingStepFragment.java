package com.karim.cakes.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karim.cakes.R;
import com.karim.cakes.model.Step;

import java.util.List;

public class CookingStepFragment extends Fragment {

    private List<Step> mSteps;
    private int mIndex;
    private Step mStep;
    private String mInstructionText = "";
    private String mUrl = "";

    public CookingStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(getString(R.string.step_details));
            //mUrl = savedInstanceState.getString(getString(R.string.url));
        } else {
            mStep = mSteps.get(mIndex);
        }

        View rootView = inflater.inflate(R.layout.fragment_cooking_step, container, false);
        TextView cookingStepText = rootView.findViewById(R.id.fragment_text);
        mInstructionText = mStep.getDescription();
        cookingStepText.setText(mInstructionText);
        return rootView;
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(getString(R.string.step_details), mStep);
        //outState.putString(getString(R.string.url), mUrl);
    }
}
