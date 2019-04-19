package com.chetan.foodrecipe;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chetan.foodrecipe.adapter.OnRecipeListener;
import com.chetan.foodrecipe.adapter.RecipeRecyclerAdapter;
import com.chetan.foodrecipe.models.Recipe;
import com.chetan.foodrecipe.viewmodels.RecipeListViewModel;

import java.util.List;


public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private static final String TAG = RecipeListActivity.class.getSimpleName();

    private RecyclerView mRecipeRecyclerView;
    private RecipeListViewModel mRecipeListViewModel;
    private RecipeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

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
                    if(mRecipeListViewModel.isViewingRecipe()) {
                        Log.d(TAG, "onChanged: " + recipes.size());
                        mRecipeListViewModel.setIsPerformingQuery(false);
                        mAdapter.setRecipes(recipes);
                    }
                }
            }
        });
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecipeRecyclerView.setAdapter(mAdapter);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRecipeRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(!mRecipeRecyclerView.canScrollVertically(1)){
                        mRecipeListViewModel.searchNextPage();
                    }
                }
            });
        }
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

    @Override
    public void onBackPressed() {
        if(mRecipeListViewModel.onBackPressed()){
            super.onBackPressed();
        }else{
            displayCategoryList();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_list_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_show_category){
            displayCategoryList();
        }
        return super.onOptionsItemSelected(item);
    }
}
