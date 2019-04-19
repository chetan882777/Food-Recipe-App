package com.chetan.foodrecipe.requests;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.chetan.foodrecipe.AppExecuters;
import com.chetan.foodrecipe.models.Recipe;
import com.chetan.foodrecipe.requests.responses.RecipeSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.chetan.foodrecipe.util.Constants.API_KEY;
import static com.chetan.foodrecipe.util.Constants.BASE_URL;
import static com.chetan.foodrecipe.util.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    private RetirveRecipesRunnable mRetirveRecipesRunnable;

    private static final String TAG = "RecipeApiClient";

    public static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    public RecipeApiClient() {
        mRecipes = new MutableLiveData<>();
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void searchRecipeApi(String query , int pageNumber) {
        if(mRetirveRecipesRunnable != null){
            mRetirveRecipesRunnable = null;
        }
        mRetirveRecipesRunnable = new RetirveRecipesRunnable(query , pageNumber);
        final Future handler = AppExecuters.getInstance().NetworkIO().submit(mRetirveRecipesRunnable);

        AppExecuters.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {

                // let user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetirveRecipesRunnable implements Runnable {
        private String query;
        private int pageNumber;
        private boolean cancelRequest;


        public RetirveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse) response.body()).getRecipes());
                    if (pageNumber == 1) {
                        mRecipes.postValue(list);
                    } else {
                        List<Recipe> currentRecipes = mRecipes.getValue();
                        currentRecipes.addAll(list);
                        mRecipes.postValue(currentRecipes);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }
        }


        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber) {
            return ServiceGenerator.getRecipeApi().searchRecipe(
                    API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest(){
            cancelRequest = true;
        }
    }

    public void cancelRequest(){
        if(mRetirveRecipesRunnable != null) {
            mRetirveRecipesRunnable.cancelRequest();
        }
    }
}
