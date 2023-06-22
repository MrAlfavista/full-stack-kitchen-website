package com.testproject.demo.controller;

import com.testproject.demo.model.DishIngredient;
import com.testproject.demo.model.DishIngredientIds;
import com.testproject.demo.service.DishIngredientService;
import com.testproject.demo.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class DishIngredientController {

    DishIngredientService dishIngredientService;
    DishService dishService;

    @Autowired
    public DishIngredientController(DishIngredientService dishIngredientService, DishService dishService) {
        this.dishIngredientService = dishIngredientService;
        this.dishService = dishService;
    }


    @PostMapping("/addDishIngredient")
    public int addDishIngredient(@RequestBody DishIngredientIds dishIngredientIds){
        dishIngredientService.save(dishIngredientIds.getDish_id(), dishIngredientIds.getIngredient_id(),
                dishIngredientIds.getCount());
        return 0;
    }

    @GetMapping("/getDishPrice")
    public Double getDishPrice(@RequestParam int dishId){
        return dishIngredientService.getDishPrice(dishService.findById(dishId));
    }

    @GetMapping("/getDishCalories")
    public Double getDishCalories(@RequestParam int dishId){
        return dishIngredientService.getDishCalories(dishService.findById(dishId));
    }

    @GetMapping("/getDishIngredients")
    public List<DishIngredient> getDishIngredients(@RequestParam int dishId){
        return dishIngredientService.findAllDishIngredients(dishService.findById(dishId));
    }
}
