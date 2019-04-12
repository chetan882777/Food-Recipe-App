package com.chetan.foodrecipe.requests;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.chetan.foodrecipe.util.Constants.BASE_URL;

public class ServiceGenerator{

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RecipeApi recipeApi = retrofit.create(RecipeApi.class);

    public static RecipeApi getRecipeApi(){
        return recipeApi;
    }
}
