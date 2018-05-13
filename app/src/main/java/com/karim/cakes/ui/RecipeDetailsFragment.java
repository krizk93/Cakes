package com.karim.cakes.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.karim.cakes.R;
import com.karim.cakes.adapters.IngredientsAdapter;
import com.karim.cakes.adapters.StepsAdapter;
import com.karim.cakes.model.Ingredient;
import com.karim.cakes.model.Recipe;
import com.karim.cakes.model.Step;

import java.util.List;

public class RecipeDetailsFragment extends Fragment implements StepsAdapter.StepsOnClickListener {

    onRecipeClickListener mCallback;

    public RecipeDetailsFragment() {

    }

    @Override
    public void onItemClick(int index) {
        mCallback.onStepSelected(index);
    }

    public interface onRecipeClickListener {
        Recipe onRecipeSelected();
        void onStepSelected(int index);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (onRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement onRecipeClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        RecyclerView ingredientsRecyclerView = RootView.findViewById(R.id.ingredients_recycler_view);
        List<Ingredient> ingredients = mCallback.onRecipeSelected().getIngredients();
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getActivity(), ingredients);
        RecyclerView.LayoutManager ingredientLayoutManager = new LinearLayoutManager(getActivity());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        ingredientsRecyclerView.setLayoutManager(ingredientLayoutManager);

        RecyclerView stepsRecyclerView = RootView.findViewById(R.id.steps_recycler_view);
        List<Step> steps = mCallback.onRecipeSelected().getSteps();
        StepsAdapter stepsAdapter = new StepsAdapter(getActivity(),steps,this);
        /*stepsRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"You clicked " + i, Toast.LENGTH_SHORT).show();
            }
        });*/

        RecyclerView.LayoutManager stepsLayoutManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsRecyclerView.setLayoutManager(stepsLayoutManager);
        return RootView;
    }
}
