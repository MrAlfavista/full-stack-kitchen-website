package com.testproject.demo.model;

public class DishIngredientIds {
    private int dish_id;
    private int ingredient_id;
    private double count;

    public DishIngredientIds(int dish_id, int ingredient_id, double count) {
        this.dish_id = dish_id;
        this.ingredient_id = ingredient_id;
        this.count = count;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
