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
    // http://localhost:8080/products/details/1
    @GetMapping("/details/{id}")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.findById(Long.valueOf(id)));
        model.addAttribute("reviews", productService.getReviews(Long.valueOf(id)));
        model.addAttribute("review", new Review());
        return "product_details";
    }
    // http://localhost:8080/products/new
    @RequestMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }

    // http://localhost:8080/products/edit/1
    @GetMapping("/edit/{id}")
    public String editById(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.findById(Long.valueOf(id)));
        return "product_form";
    }

    @PostMapping("/save")
    public String saveOrUpdate(@ModelAttribute Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "product_form";
        }
        if (product.getId() != null) {
            productService.edit(product);
        }
        else {
            productService.save(product);
        }
        return "redirect:/products/list" ;
    }

    // http://localhost:8080/products/delete/1
    @RequestMapping("/delete/{id}")
    public String deleteById(@PathVariable String id){
        productService.deleteById(Long.valueOf(id));
        return "redirect:/products/list";
    }


    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}





