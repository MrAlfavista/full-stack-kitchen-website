package com.testproject.demo.service;

import com.testproject.demo.model.*;
import com.testproject.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserIngredientService {

    private final UserIngredientRepository userIngredientRepository;
    private final DishIngredientRepository dishIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    @Autowired
    public UserIngredientService(UserIngredientRepository userIngredientRepository, DishIngredientRepository dishIngredientRepository, IngredientRepository ingredientRepository, UserRepository userRepository, DishRepository dishRepository){
        this.userIngredientRepository = userIngredientRepository;
        this.dishIngredientRepository = dishIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;

        this.dishRepository = dishRepository;
    }

    public void addIngredient(User user, Ingredient ingredient, Double count){
        userIngredientRepository.save(user.getId(), ingredient.getId(), count);
    }

    @Transactional
    public void addIngredient(int userId, int ingredientId, Double count){
        User user = userRepository.findById(userId);
        Ingredient ingredient = ingredientRepository.findById(ingredientId);
        if(!userIngredientRepository.existsUserIngredientByUserAndIngredient(user, ingredient)){
            userIngredientRepository.save(userId, ingredientId, count);
        } else {
            System.out.println("exists");
            Double prevCount = userIngredientRepository.findByUserAndIngredient(user, ingredient).getCount();
            userIngredientRepository.deleteByUserAndIngredient(user, ingredient);
            userIngredientRepository.save(userId, ingredientId, prevCount+count);
        }
    }

    public List<UserIngredient> findAllByUser(User user){
        return userIngredientRepository.findAllByUser(user);
    }

    public void deleteIngredient(User user, Ingredient ingredient){
        userIngredientRepository.deleteByUserAndIngredient(user, ingredient);
    }


    public boolean canMake(User user, Dish dish){
        for(DishIngredient i: dishIngredientRepository.findAllByDish(dish)){
            UserIngredient userIngredient = userIngredientRepository.findByUserAndIngredient(user, i.getIngredient());
            if(userIngredient==null || userIngredient.getCount() < i.getCount()){
                return false;
            }
        }
        return true;
    }

    public void deleteDish(User user, Dish dish){
        if(!canMake(user, dish)) return;
        for(DishIngredient i: dishIngredientRepository.findAllByDish(dish)){
            Double countBefore = userIngredientRepository.findByUserAndIngredient(user, i.getIngredient()).getCount();
            userIngredientRepository.deleteByUserAndIngredient(user, i.getIngredient());
            userIngredientRepository.save(dish.getId(), i.getIngredient().getId(), countBefore - i.getCount());
        }
    }

    public List<Dish> possibleDishes(User user){
        List<Dish> result = new ArrayList<>();
        for(Dish i: dishRepository.findAll()){
            if(canMake(user, i)) result.add(i);
        }
        return result;
    }

    //Random dish

    //cheapest dish

    //Maximum calories?

    //how much left?
}
