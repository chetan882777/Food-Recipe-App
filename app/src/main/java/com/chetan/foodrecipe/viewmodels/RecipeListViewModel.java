package com.chetan.foodrecipe.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.chetan.foodrecipe.models.Recipe;
import com.chetan.foodrecipe.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository mRecipeRepository;
    private boolean mIsViewingRecipe;
    private boolean mIsPerformingQuery;

    public RecipeListViewModel(){
        mIsViewingRecipe = false;
        mIsPerformingQuery = false;
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }

    public void searchRecipeApi(String query, int pageNumber){
        mIsViewingRecipe = true;
        mIsPerformingQuery = true;
        mRecipeRepository.searchRecipeApi(query , pageNumber);
    }

    public boolean isViewingRecipe() {
        return mIsViewingRecipe;
    }

    public void setIsViewingRecipe(boolean isViewingRecipe) {
        mIsViewingRecipe = isViewingRecipe;
    }

    public boolean isPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void setIsPerformingQuery(boolean isPerformingQuery) {
        mIsPerformingQuery = isPerformingQuery;
    }

    public boolean onBackPressed(){
        if(mIsPerformingQuery){
            mRecipeRepository.cancelRequest();
            mIsPerformingQuery = false;
        }
        else if(mIsViewingRecipe){
            mIsViewingRecipe = false;
            return false;
        }
        return true;
    }
}
