package com.awbd.proiect1.service;

import com.awbd.proiect1.domain.Product;
import com.awbd.proiect1.domain.Review;
import com.awbd.proiect1.domain.User;
import com.awbd.proiect1.exception.ResourceNotFoundException;
import com.awbd.proiect1.repository.ProductRepository;
import com.awbd.proiect1.repository.ReviewRepository;
import com.awbd.proiect1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;


    public Review addReview(Review review, Long idProduct, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        review.setProduct(product);
        review.setUser(user);

        Review savedReview = reviewRepository.save(review);

        return savedReview;
    }
}
