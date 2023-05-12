package com.awbd.proiect1.service;

import com.awbd.proiect1.domain.Cart;
import com.awbd.proiect1.domain.User;
import com.awbd.proiect1.domain.UserType;
import com.awbd.proiect1.repository.CartRepository;
import com.awbd.proiect1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found!");
        }
        return userOptional.get();
    }



    public User save(User user) throws Exception {
        Optional<User> userExists = userRepository.findByUsername(user.getUsername());
        if (userExists.isPresent()) {
            throw new Exception();
        }

        // encrypt the password
        String pass = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode(pass);
        pass = "{bcrypt}" + result;
        user.setPassword(pass);

        user.setEnabled(1);
        user.setUserType(UserType.CUSTOMER);
        user.setRegDate(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(user);

        return savedUser;
    }


    public List<User> findAll() {
        List<User> users = new LinkedList<>();
        userRepository.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

}
