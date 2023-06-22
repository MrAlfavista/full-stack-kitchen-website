package com.testproject.demo.repository;

import com.testproject.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user (`login`, `password_sha`) VALUES (?1, CONCAT('1be3db47a7684152', ?1, ?2))", nativeQuery = true)
    void save(String login, String password);

    void deleteByLogin(String login);

    @Query(value = "SELECT * FROM `user`", nativeQuery = true)
    List<User> findAll();

    User findById(int id);

    User findByLoginAndPasswordSha(String login, String passwordSha);
}
