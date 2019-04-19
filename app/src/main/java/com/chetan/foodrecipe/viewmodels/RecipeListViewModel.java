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

    public RecipeListViewModel(){
        mIsViewingRecipe = false;
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }

    public void searchRecipeApi(String query, int pageNumber){
        mIsViewingRecipe = true;
        mRecipeRepository.searchRecipeApi(query , pageNumber);
    }

    public boolean isViewingRecipe() {
        return mIsViewingRecipe;
    }

    public void setIsViewingRecipe(boolean isViewingRecipe) {
        mIsViewingRecipe = isViewingRecipe;
    }
}
