package com.testproject.demo.repository;

import com.testproject.demo.model.Ingredient;
import com.testproject.demo.model.User;
import com.testproject.demo.model.UserIngredient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserIngredientRepository extends JpaRepository<UserIngredient, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_ingredient (`user_id`, `ingredient_id`, `count`) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void save(int userId, int ingredientId, Double count);

    boolean existsUserIngredientByUserAndIngredient(User user, Ingredient ingredient);

    List<UserIngredient> findAllByUser(User user);

    void deleteByUserAndIngredient(User user, Ingredient ingredient);

    UserIngredient findByUserAndIngredient(User user, Ingredient ingredient);

}
