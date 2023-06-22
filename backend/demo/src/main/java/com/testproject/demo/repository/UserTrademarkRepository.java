package com.testproject.demo.repository;

import com.testproject.demo.model.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTrademarkRepository extends JpaRepository<UserTrademark, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_trademark (`user_id`, `trademark_id`, `count`) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void save(int userId, int trademarkId, Double count);

    //boolean existsUserIngredientByUserAndIngredient(User user, Trademark trademark);

    List<UserTrademark> findAllByUser(User user);

    void deleteByUserAndTrademark(User user, Trademark trademark);

    UserTrademark findByUserAndTrademark(User user, Trademark trademark);

}
