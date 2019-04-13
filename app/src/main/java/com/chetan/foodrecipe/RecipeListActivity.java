package com.chetan.foodrecipe;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chetan.foodrecipe.adapter.OnRecipeListener;
import com.chetan.foodrecipe.adapter.RecipeRecyclerAdapter;
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

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private static final String TAG = RecipeListActivity.class.getSimpleName();

    private RecyclerView mRecipeRecyclerView;
    private RecipeListViewModel mRecipeListViewModel;
    private RecipeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecipeRecyclerView = findViewById(R.id.recipe_recycleView);

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);

        initRecyclerView();
        subscribeObserver();
        testRetrofitRequest();

    }
    private void subscribeObserver(){
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if(recipes != null){
                        Log.d(TAG, "onChanged: " + recipes.size());
                        mAdapter.setRecipes(recipes);
                }
            }
        });
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecipeRecyclerView.setAdapter(mAdapter);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private  void testRetrofitRequest(){
        mRecipeListViewModel.searchRecipeApi("indian" , 1);
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}
