package com.awbd.proiect1.service;

import com.awbd.proiect1.domain.Cart;
import com.awbd.proiect1.domain.Product;
import com.awbd.proiect1.domain.User;
import com.awbd.proiect1.repository.CartRepository;
import com.awbd.proiect1.repository.ProductRepository;
import com.awbd.proiect1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    CartService cartService;

    private final Long id = 1L;

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CartRepository cartRepository;

    @Test
    void getCartItems() {
        User user = new User();
        user.setId(id);
        user.setUsername("username");
        Cart cart = new Cart();
        user.setCart(cart);

        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        cartService.getCartItems("username");
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    public void addToCart() {
        User user = new User();
        user.setId(id);
        user.setUsername("username");

        Product product = new Product();
        product.setId(id);

        Cart cart = new Cart();
        user.setCart(cart);

        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        cartService.addToCart("username", id);
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(productRepository, times(1)).findById(id);
        verify(cartRepository, times(1)).save(cart);
    }
}