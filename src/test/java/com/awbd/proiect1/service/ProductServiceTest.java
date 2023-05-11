package com.awbd.proiect1.service;

import com.awbd.proiect1.domain.Product;
import com.awbd.proiect1.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    private final Long id = 1L;

    @Test
    void findAllHasContent() {
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        p1.setId(id);
        Product p2 = new Product();
        p2.setId(id+1);
        products.add(p1);
        products.add(p2);
        when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(products));
        List<Product> productResult = productService.findAll(0,10, "id");
        assertEquals(products.size(),productResult.size());
    }

    @Test
    void findAllNoContent() {
        List<Product> products = new ArrayList<>();
        when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(products));
        List<Product> productResult = productService.findAll(0,10, "id");
        assertEquals(products.size(),productResult.size());
    }

    @Test
    public void deleteById() {
        Product product = new Product();
        product.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productService.deleteById(id);

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).deleteById(id);
    }
}