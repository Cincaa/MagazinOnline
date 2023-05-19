package com.awbd.proiect1.controller;

import com.awbd.proiect1.domain.Order;
import com.awbd.proiect1.domain.UserType;
import com.awbd.proiect1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

public class OrderController {

    @Autowired
    OrderService orderService;

    // http://localhost:8080/orders/place_order
    @RequestMapping("/place_order")
    public String placeOrder() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(String.valueOf(UserType.CUSTOMER)))) {
            Order order = orderService.placeOrder(auth.getName());
            System.out.println(order);
        }

        return "redirect:/orders/list";
    }
}
