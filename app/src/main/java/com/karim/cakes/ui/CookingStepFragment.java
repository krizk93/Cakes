package com.karim.cakes.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karim.cakes.R;

public class CookingStepFragment extends Fragment {

    private String mInstructionText = "";
    private String mUrl = "";

    public CookingStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mInstructionText = savedInstanceState.getString(getString(R.string.instruction_text));
            mUrl = savedInstanceState.getString(getString(R.string.url));
        }

        View rootView = inflater.inflate(R.layout.fragment_cooking_step, container, false);
        TextView cookingStepText = rootView.findViewById(R.id.fragment_text);
        cookingStepText.setText(mInstructionText);
        return rootView;
    }

    public void setInstructionText(String text) {
        mInstructionText = text;
    }

    public void setVideoUrl(String url) {
        mUrl = url;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.url), mUrl);
        outState.putString(getString(R.string.instruction_text), mInstructionText);
    }
}
