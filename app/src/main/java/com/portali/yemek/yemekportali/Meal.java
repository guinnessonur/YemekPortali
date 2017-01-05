package com.portali.yemek.yemekportali;

/**
 * Created by GURKAN32 on 1/5/2017.
 */

public class Meal {
    String name;
    String recipe;
    int time;
    int rating;
    String ingredients;
    String type;

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public int getTime() {
        return time;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getType() {
        return type;
    }
}
