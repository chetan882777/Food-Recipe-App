package com.chetan.foodrecipe.requests;

import android.arch.lifecycle.MutableLiveData;

import com.chetan.foodrecipe.AppExecuters;
import com.chetan.foodrecipe.models.Recipe;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.chetan.foodrecipe.util.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;

    public static RecipeApiClient getInstance(){
        if(instance == null){
            instance = new RecipeApiClient();
        }
        return instance;
    }

    public RecipeApiClient(){
        mRecipes = new MutableLiveData<>();
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void searchRecipeApi(){
        final Future handler = AppExecuters.getInstance().NetworkIO().submit(new Runnable() {
            @Override
            public void run() {

                // retrieve data from rest api
                // mRecipes.postValue();
            }
        });

        AppExecuters.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {

                // let user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }
}
