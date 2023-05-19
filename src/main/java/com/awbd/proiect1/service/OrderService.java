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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public List<Order> findAll() {
        List<Order> orders = new LinkedList<>();
        orderRepository.findAll().iterator().forEachRemaining(orders::add);
        return orders;
    }


    public List<Order> findUserOrders(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Order> orders = user.getOrders();
        return orders;
    }


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


    public List<Product> getProducts(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new RuntimeException("Order not found!");
        }
        Order order = orderOptional.get();
        List<Product> products = order.getProducts();

        return products;
    }
}
