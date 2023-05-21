package com.awbd.proiect1.service;

import com.awbd.proiect1.domain.Product;
import com.awbd.proiect1.domain.Review;
import com.awbd.proiect1.domain.User;
import com.awbd.proiect1.repository.ProductRepository;
import com.awbd.proiect1.repository.ReviewRepository;
import com.awbd.proiect1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    ReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;

    private final Long id = 1L;

    @Mock
    ProductRepository productRepository;

    @Mock
    UserRepository userRepository;


    @Test
    public void addReview() {
        User user = new User();
        user.setId(id);
        user.setUsername("username");

        Product product = new Product();
        product.setId(id);

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setComment("comm");

        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        reviewService.addReview(review, id, "username");
        verify(reviewRepository, times(1)).save(review);
    }
}