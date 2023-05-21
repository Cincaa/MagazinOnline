package com.awbd.proiect1.controller;

import com.awbd.proiect1.domain.Review;
import com.awbd.proiect1.domain.UserType;
import com.awbd.proiect1.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    // http://localhost:8080/add_review/1
    @PostMapping("/add_review/{idProduct}")
    public String addReview(@ModelAttribute Review review, @PathVariable Long idProduct) {

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(String.valueOf(UserType.CUSTOMER)))) {
            reviewService.addReview(review, idProduct, auth.getName());
        }

        return "redirect:/products/details/" + idProduct;
    }
}
