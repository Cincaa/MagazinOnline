package com.awbd.proiect1.controller;

import com.awbd.proiect1.domain.Product;
import com.awbd.proiect1.domain.Review;
import com.awbd.proiect1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;


    // http://localhost:8080/products/list
    // http://localhost:8080/products/list?pageNo=0&pageSize=2
    // http://localhost:8080/products/list?sortBy=Price
    @RequestMapping("/list")
    public ModelAndView productsList(@RequestParam(defaultValue = "0") Integer pageNo,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        ModelAndView modelAndView = new ModelAndView("products");
        List<Product> products = productService.findAll(pageNo, pageSize, sortBy);
        modelAndView.addObject("products", products);
        return modelAndView;
    }


    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}





