package com.awbd.proiect1.service;

import com.awbd.proiect1.domain.Cart;
import com.awbd.proiect1.domain.Product;
import com.awbd.proiect1.domain.User;
import com.awbd.proiect1.exception.ResourceNotFoundException;
import com.awbd.proiect1.repository.CartRepository;
import com.awbd.proiect1.repository.ProductRepository;
import com.awbd.proiect1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;


    public List<Product> getCartItems(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = user.getCart();

        List<Product> products = cart.getProducts();

        return products;
    }

    public void addToCart(String username, Long idProduct) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Cart cart = user.getCart();

        cart.getProducts().add(product);
        cartRepository.save(cart);
    }
    public void removeFromCart(String username, Long idProduct) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Cart cart = user.getCart();

        cart.getProducts().remove(product);
        cartRepository.save(cart);
    }
}
