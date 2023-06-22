package com.testproject.demo.repository;

import com.testproject.demo.model.Dish;
import com.testproject.demo.model.Ingredient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    Dish findByName(String name);

    Dish findById(int id);

    List<Dish> findAllByNameContaining(String name);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO dish (`name`, `recipe`, `calories`, `price`, `image_url`) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    void save(String name, String recipe, double calories, double price, String imageUrl);

    void deleteByName(String name);

    @Query(value = "SELECT * FROM `dish`", nativeQuery = true)
    List<Dish> findAll();
}
