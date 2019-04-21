package com.chetan.foodrecipe.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.chetan.foodrecipe.models.Recipe;
import com.chetan.foodrecipe.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;
    private String mQuery;
    private int mPageNumber;
    private MutableLiveData<Boolean> isQueryExhousted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();

    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository(){
        mRecipeApiClient = RecipeApiClient.getInstance();
    }

    private void initMediators(){
        LiveData<List<Recipe>> recipeListApiSource = mRecipeApiClient.getRecipes();
        mRecipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if(recipes != null){

                    mRecipes.setValue(recipes);
                    doneQuery(recipes);
                }else{
                    doneQuery(null);
                }
            }
        });
    }

    private void doneQuery(List<Recipe> list){
        if(list != null){
            if(list.size() % 30 != 0){
                isQueryExhousted.setValue(true);
            }
        }else{
            isQueryExhousted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhousted(){ return isQueryExhousted; }

    public LiveData<List<Recipe>> getRecipes(){return mRecipes;}

    public LiveData<Recipe> getRecipe(){ return  mRecipeApiClient.getRecipe();}

    public LiveData<Boolean> isNetworkTimedOut(){return mRecipeApiClient.isNetworkTimedOut();}

    public void searchRecipeApi(String query , int pageNumber){
        if(pageNumber == 0){
            pageNumber =1 ;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        isQueryExhousted.setValue(false);
        mRecipeApiClient.searchRecipeApi(query , pageNumber);
    }

    public void searchRecipeById(String recipeId){ if(recipeId != null){ mRecipeApiClient.searchRecipeById(recipeId);}}

    public void searchNextPage(){
        searchRecipeApi(mQuery , mPageNumber + 1);
    }

    public void cancelRequest(){
        mRecipeApiClient.cancelRequest();
    }
}
