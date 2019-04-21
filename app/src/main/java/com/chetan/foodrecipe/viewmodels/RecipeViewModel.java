package com.chetan.foodrecipe.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.chetan.foodrecipe.models.Recipe;
import com.chetan.foodrecipe.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private String mRecipeId;

    private Boolean didiRetrieveRecipe;



    public RecipeViewModel(){
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public String getRecipeId(){
        return mRecipeId;
    }

    public LiveData<Recipe> getRecipe(){
        return mRecipeRepository.getRecipe();
    }

    public LiveData<Boolean> isNetworkTimedOut(){return mRecipeRepository.isNetworkTimedOut();}

    public void searchRecipeById(String recipeId){
        mRecipeId = recipeId;
        mRecipeRepository.searchRecipeById(recipeId);
    }

    public Boolean getDidiRetrieveRecipe() {
        return didiRetrieveRecipe;
    }

    public void setDidiRetrieveRecipe(Boolean didiRetrieveRecipe) {
        this.didiRetrieveRecipe = didiRetrieveRecipe;
    }
}
