package com.chetan.foodrecipe;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chetan.foodrecipe.models.Recipe;
import com.chetan.foodrecipe.requests.RecipeApi;
import com.chetan.foodrecipe.requests.ServiceGenerator;
import com.chetan.foodrecipe.requests.responses.RecipeSearchResponse;
import com.chetan.foodrecipe.util.Constants;
import com.chetan.foodrecipe.viewmodels.RecipeListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {

    private static final String TAG = RecipeListActivity.class.getSimpleName();

    private RecipeListViewModel mRecipeListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        subscribeObserver();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofitRequest();
            }
        });
    }

    private void subscribeObserver(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if(recipes != null){
                    for(Recipe recipe : recipes){
                        Log.d(TAG, "onChanged: " + recipe.getTitle());
                    }
                }
            }
        });
    }


    private  void testRetrofitRequest(){
        mRecipeListViewModel.searchRecipeApi("curry" , 1);
    }
}
