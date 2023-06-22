package com.testproject.demo.service;

import com.testproject.demo.model.Dish;
import com.testproject.demo.model.DishIngredient;
import com.testproject.demo.model.Ingredient;
import com.testproject.demo.repository.DishIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishIngredientService {

    private final DishIngredientRepository dishIngredientRepository;

    @Autowired
    public DishIngredientService(DishIngredientRepository dishIngredientRepository) {
        this.dishIngredientRepository = dishIngredientRepository;
    }

    public void save(Dish dish, Ingredient ingredient, Double count){
        dishIngredientRepository.save(dish.getId(), ingredient.getId(), count);
    }

    public void save(int dishId, int ingredientId, Double count){
        dishIngredientRepository.save(dishId, ingredientId, count);
    }

    public void deleteIngredient(Dish dish, Ingredient ingredient){
        dishIngredientRepository.deleteByDishAndIngredient(dish, ingredient);
    }

    public List<DishIngredient> findAllDishIngredients(Dish dish){
        return dishIngredientRepository.findAllByDish(dish);
    }


    public Double getDishPrice(Dish dish){
        double sum = 0;
        for(DishIngredient i: dishIngredientRepository.findAllByDish(dish)){
            sum+=i.getIngredient().getPrice()*i.getCount();
        }
        return sum;
    }

    public Double getDishCalories(Dish dish){
        double sum = 0;
        for(DishIngredient i: dishIngredientRepository.findAllByDish(dish)){
            sum+=i.getIngredient().getCalories();
        }
        return sum;
    }
}
