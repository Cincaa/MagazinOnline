package com.awbd.proiect1.controller;

import com.awbd.proiect1.domain.Order;
import com.awbd.proiect1.domain.Product;
import com.awbd.proiect1.domain.UserType;
import com.awbd.proiect1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class OrderController {

    @Autowired
    OrderService orderService;


    // http://localhost:8080/orders/list
    @RequestMapping("/list")
    public ModelAndView ordersList() {
        ModelAndView modelAndView = new ModelAndView("orders");

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(String.valueOf(UserType.ADMIN)))) {
            List<Order> orders = orderService.findAll();
            modelAndView.addObject("orders", orders);
        }
        else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(String.valueOf(UserType.CUSTOMER)))) {
            List<Order> orders = orderService.findUserOrders(auth.getName());
            modelAndView.addObject("orders", orders);
        }
        else {
            modelAndView.addObject("error");
        }

        return modelAndView;
    }


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


    // http://localhost:8080/orders/details/1
    @GetMapping("/details/{id}")
    public String showById(@PathVariable String id, Model model) {
        List<Product> products = orderService.getProducts(Long.valueOf(id));
        model.addAttribute("products", products);
        return "order_details";
    }
}
