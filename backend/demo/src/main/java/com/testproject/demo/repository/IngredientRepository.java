package com.testproject.demo.repository;

import com.testproject.demo.model.Ingredient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO ingredient (`name`, `price`, `calories`, `measure`, `amount`, `image_url`) VALUES (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    void save(String name, double price, double calories, String measure, double amount, String imageUrl);

    List<Ingredient> findAllByNameContaining(String name);
    Ingredient findByName(String name);

    Ingredient findById(int id);

    void deleteByName(String name);

    @Query(value = "SELECT * FROM `ingredient`", nativeQuery = true)
    List<Ingredient> findAll();
}
