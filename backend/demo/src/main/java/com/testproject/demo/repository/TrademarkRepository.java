package com.testproject.demo.repository;

import com.testproject.demo.model.Ingredient;
import com.testproject.demo.model.Trademark;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrademarkRepository extends JpaRepository<Trademark, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO trademark (`name`, `price`, `calories`, `measure`, `amount`, `image_url`, `ingredient_id`) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
    void save(String name, double price, double calories, String measure, double amount, String imageUrl, int ingredientId);

    List<Trademark> findAllByNameContaining(String name);
    Trademark findByName(String name);

    Trademark findById(int id);

    void deleteByName(String name);

    @Query(value = "SELECT * FROM `trademark`", nativeQuery = true)
    List<Trademark> findAll();

    List<Trademark> findAllByIngredient(Ingredient ingredient);
}
