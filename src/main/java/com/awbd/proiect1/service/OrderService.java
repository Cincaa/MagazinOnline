package com.awbd.proiect1.service;

import com.awbd.proiect1.domain.Cart;
import com.awbd.proiect1.domain.Order;
import com.awbd.proiect1.domain.Product;
import com.awbd.proiect1.domain.User;
import com.awbd.proiect1.exception.ResourceNotFoundException;
import com.awbd.proiect1.repository.CartRepository;
import com.awbd.proiect1.repository.OrderRepository;
import com.awbd.proiect1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public Order placeOrder(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = user.getCart();

        List<Product> products = cart.getProducts();

        Order order = new Order();
        order.setUser(user);
        order.setProducts(products);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("Processing");

        float total = 0;
        for(Product p : products) {
            total += p.getPrice();
        }

        order.setTotal(total);

        cart.setProducts(new ArrayList<>());
        cartRepository.save(cart);

        Order savedOrder = orderRepository.save(order);

        return savedOrder;
    }
}