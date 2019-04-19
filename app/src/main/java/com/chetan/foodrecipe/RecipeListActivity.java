package com.chetan.foodrecipe;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.chetan.foodrecipe.adapter.OnRecipeListener;
import com.chetan.foodrecipe.adapter.RecipeRecyclerAdapter;
import com.chetan.foodrecipe.models.Recipe;
import com.chetan.foodrecipe.viewmodels.RecipeListViewModel;

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
        initSearchView();

        if(!mRecipeListViewModel.isViewingRecipe()){
            displayCategoryList();
        }
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

    private void initSearchView(){
        SearchView searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.displayLoading();
                mRecipeListViewModel.searchRecipeApi(query , 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {
        mAdapter.displayLoading();
        mRecipeListViewModel.searchRecipeApi(category, 1);
    }

    private void displayCategoryList(){
        mRecipeListViewModel.setIsViewingRecipe(false);
        mAdapter.displayCategoryList();
    }
}
