package com.awbd.proiect1.controller;

import com.awbd.proiect1.domain.Product;
import com.awbd.proiect1.domain.UserType;
import com.awbd.proiect1.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    // http://localhost:8080/cart/list
    @RequestMapping("/list")
    public ModelAndView cartList() {
        ModelAndView modelAndView = new ModelAndView("cart");

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(String.valueOf(UserType.CUSTOMER)))) {
            List<Product> products = cartService.getCartItems(auth.getName());
            modelAndView.addObject("products", products);
        }
        else {
            modelAndView.addObject("error");
        }

        return modelAndView;
    }

    // http://localhost:8080/cart/add/1
    @RequestMapping("/add/{id}")
    public String addToCart(@PathVariable String id, RedirectAttributes redirectAttrs) {

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(String.valueOf(UserType.CUSTOMER)))) {

            cartService.addToCart(auth.getName(), Long.valueOf(id));
        }

        return "redirect:/products/list";
    }


}
