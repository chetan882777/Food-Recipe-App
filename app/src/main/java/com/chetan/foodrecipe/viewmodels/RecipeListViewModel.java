package com.chetan.foodrecipe.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.chetan.foodrecipe.models.Recipe;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();

    public RecipeListViewModel(){}

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }
}
