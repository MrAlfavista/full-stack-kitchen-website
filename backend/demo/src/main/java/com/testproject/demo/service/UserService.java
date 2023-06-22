package com.testproject.demo.service;

import com.testproject.demo.model.User;
import com.testproject.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void save(String login, String password){
        userRepository.save(login, password);
    }

    public void deleteByLogin(String login){
        userRepository.deleteByLogin(login);
    }

    public User findById(int id){
        return userRepository.findById(id);
    }

    public User findByLoginAndPasswordSha(String login, String passwordSha){
        return userRepository.findByLoginAndPasswordSha(login, passwordSha);
    }

    public User login(String login, String password){
        return findByLoginAndPasswordSha(login, "1be3db47a7684152" + login + password);
    }
}
