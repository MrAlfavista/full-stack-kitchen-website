package com.testproject.demo.repository;

import com.testproject.demo.model.Dish;
import com.testproject.demo.model.DishIngredient;
import com.testproject.demo.model.Ingredient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishIngredientRepository extends JpaRepository<DishIngredient, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO dish_ingredient (`dish_id`, `ingredient_id`, `count`) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void save(int dishId, int ingredientId, Double count);

    void deleteByDishAndIngredient(Dish dish, Ingredient ingredient);

    List<DishIngredient> findAllByDish(Dish dish);
}
