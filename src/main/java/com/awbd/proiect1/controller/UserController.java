package com.awbd.proiect1.controller;

import com.awbd.proiect1.domain.User;
import com.awbd.proiect1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    // http://localhost:8080/users/signup
    @RequestMapping("/signup")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }


    @PostMapping("/save")
    public String saveOrUpdate(@ModelAttribute User user, Model model) {
        try {
            User savedUser = userService.save(user);
            return "redirect:/users/details/" + savedUser.getId();
        }
        catch (Exception e) {
            model.addAttribute("errorMessage","User already exists");
            return "signup_form";
        }
    }

    @GetMapping("/details/{id}")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.findById(Long.valueOf(id)));
        return "user_details";
    }


    // http://localhost:8080/users/list
    @RequestMapping("/list")
    public ModelAndView usersList() {
        ModelAndView modelAndView = new ModelAndView("users");
        List<User> users = userService.findAll();
        modelAndView.addObject("users", users);
        return modelAndView;
    }


    // http://localhost:8080/users/delete/1
    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        userService.deleteById(Long.valueOf(id));
        return "redirect:/users/list";
    }
}
