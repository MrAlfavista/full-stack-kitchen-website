package com.testproject.demo.model;

public class UserIngredientIds {
    private int user_id;
    private int ingredient_id;
    private Double count;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public UserIngredientIds(int user_id, int ingredient_id, Double count) {
        this.user_id = user_id;
        this.ingredient_id = ingredient_id;
        this.count = count;
    }
}
