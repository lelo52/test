package com.test.SqlLogin.service;

import com.test.SqlLogin.domain.Users;
import com.test.SqlLogin.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepository userRepository;

    @Autowired
    public UsersService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users getUserByIdAndPassword(String id, String pw) {
        return userRepository.findByIdAndPw(id, pw);
    }

    public Users getUserById(String id) {
        return userRepository.findById(id);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users createUser(Users user) {
        return userRepository.save(user);
    }
}
