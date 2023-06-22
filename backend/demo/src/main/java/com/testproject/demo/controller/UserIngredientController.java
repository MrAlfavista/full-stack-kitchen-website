package com.testproject.demo.controller;

import com.testproject.demo.model.Dish;
import com.testproject.demo.model.User;
import com.testproject.demo.model.UserIngredient;
import com.testproject.demo.model.UserIngredientIds;
import com.testproject.demo.service.DishService;
import com.testproject.demo.service.UserIngredientService;
import com.testproject.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserIngredientController {
    UserIngredientService userIngredientService;
    UserService userService;
    DishService dishService;

    @Autowired
    public UserIngredientController(UserIngredientService userIngredientService, UserService userService, DishService dishService) {
        this.userIngredientService = userIngredientService;
        this.userService = userService;
        this.dishService = dishService;
    }

    @PostMapping("/addUserIngredient")
    public int addUserIngredient(@RequestBody UserIngredientIds userIngredientids){
        userIngredientService.addIngredient(userIngredientids.getUser_id(), userIngredientids.getIngredient_id(), userIngredientids.getCount());
        return 0;
    }

    @GetMapping("/getUserIngredients")
    public List<UserIngredient> getUserIngredients(@RequestParam int userId){
        User user = userService.findById(userId);
        return userIngredientService.findAllByUser(user);
    }

    @GetMapping("/canMake")
    public boolean canBeMade(@RequestParam int userId, @RequestParam int dishId){
        return userIngredientService.canMake(userService.findById(userId), dishService.findById(dishId));
    }

    @GetMapping("/possibleDishes")
    public List<Dish> getPossibleDishes(@RequestParam int userId){
        User user = userService.findById(userId);
        return userIngredientService.possibleDishes(user);
    }

    @DeleteMapping("/deleteDish")
    public void deleteDish(@RequestParam int userId, @RequestParam int dishId){
        User user = userService.findById(userId);
        Dish dish = dishService.findById(dishId);
        userIngredientService.deleteDish(user, dish);
    }

}
