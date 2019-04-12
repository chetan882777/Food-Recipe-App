package com.chetan.foodrecipe.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Recipe implements Parcelable {
    private String title;
    private String publisher;
    private String[] ingredients;
    private String recipe_id;
    private String img_url;
    private float social_rank;

    public Recipe() {
    }

    public Recipe(String title, String publisher, String[] ingredients, String recipe_id, String img_url, float social_rank) {
        this.title = title;
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.recipe_id = recipe_id;
        this.img_url = img_url;
        this.social_rank = social_rank;
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        publisher = in.readString();
        ingredients = in.createStringArray();
        recipe_id = in.readString();
        img_url = in.readString();
        social_rank = in.readFloat();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public float getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(float social_rank) {
        this.social_rank = social_rank;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", recipe_id='" + recipe_id + '\'' +
                ", img_url='" + img_url + '\'' +
                ", social_rank=" + social_rank +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeStringArray(ingredients);
        dest.writeString(recipe_id);
        dest.writeString(img_url);
        dest.writeFloat(social_rank);
    }
}
