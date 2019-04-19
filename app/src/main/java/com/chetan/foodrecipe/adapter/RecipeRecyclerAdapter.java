package com.chetan.foodrecipe.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chetan.foodrecipe.R;
import com.chetan.foodrecipe.models.Recipe;
import com.chetan.foodrecipe.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;

    private List<Recipe> mRecipes;
    private OnRecipeListener mOnRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        mOnRecipeListener = onRecipeListener;
        mRecipes = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        switch (i) { // i is the view type constant
            case RECIPE_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recipe_list_item, viewGroup, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
            }

            case LOADING_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_list_item, viewGroup, false);
                return new LoadingViewHolder(view);
            }
            case CATEGORY_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category_list_item, viewGroup, false);
                return new CategoryViewHolder(view, mOnRecipeListener);
            }

            default:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recipe_list_item, viewGroup, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int itemViewType = getItemViewType(i);
        if(itemViewType == RECIPE_TYPE) {
            // set the image
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Glide.with(((RecipeViewHolder) viewHolder).itemView)
                    .setDefaultRequestOptions(options)
                    .load(mRecipes.get(i).getImage_url())
                    .into(((RecipeViewHolder) viewHolder).image);

            ((RecipeViewHolder) viewHolder).title.setText(mRecipes.get(i).getTitle());
            ((RecipeViewHolder) viewHolder).publisher.setText(mRecipes.get(i).getPublisher());
            ((RecipeViewHolder) viewHolder).socialScore.setText(String.valueOf(Math.round(mRecipes.get(i).getSocial_rank())));
        }
        if(itemViewType == CATEGORY_TYPE) {
            // set the image
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Uri path = Uri.parse("android.resource://com.chetan.foodrecipe/drawable/" + mRecipes.get(i).getImage_url());
            Glide.with(((CategoryViewHolder) viewHolder).itemView)
                    .setDefaultRequestOptions(options)
                    .load(path)
                    .into(((CategoryViewHolder) viewHolder).categoryImageView);

            ((CategoryViewHolder) viewHolder).categoryTitle.setText(mRecipes.get(i).getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mRecipes.get(position).getSocial_rank() == -1){
            return CATEGORY_TYPE;
        }
        else if(mRecipes.get(position).getTitle().equals("LOADING...")){
            return LOADING_TYPE;
        }
        else if(position     == mRecipes.size()-1 && position != 0
                && mRecipes.get(position).getTitle().equals("EXHAUSTED...")){
            return LOADING_TYPE;
        }
        else{
            return RECIPE_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void displayLoading(){
        if(!isLoading()){
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            mRecipes = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading(){
        if(mRecipes.size() > 0){
            if(mRecipes.get(mRecipes.size() - 1).getTitle().equals("LOADING...")){
                return true;
            }
        }
        return false;
    }

    public void displayCategoryList(){
        List<Recipe> categories = new ArrayList<>();

        Log.d("RecipeAdapter" , " --------------------- recipes : " + Constants.DEFAULT_SEARCH_CATEGORIES.length);
        for(int i = 0 ; i < Constants.DEFAULT_SEARCH_CATEGORIES.length; i++){
            Recipe recipe = new Recipe();
            recipe.setImage_url(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setSocial_rank(-1);
            categories.add(recipe);
            Log.d("RecipeAdapter" , " recipe : " + recipe.getTitle());
        }
        mRecipes = categories;
        notifyDataSetChanged();
    }

    public void setRecipes(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }
}
