package com.chetan.foodrecipe.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.chetan.foodrecipe.models.Recipe;
import com.chetan.foodrecipe.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository mRecipeRepository;

    public RecipeListViewModel(){
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }

    public void searchRecipeApi(String query, int pageNumber){
        mRecipeRepository.searchRecipeApi(query , pageNumber);
    }
}
