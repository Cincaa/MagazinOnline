package com.awbd.proiect1.service;

import com.awbd.proiect1.domain.Product;
import com.awbd.proiect1.domain.Review;
import com.awbd.proiect1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<Product> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Product> pagedResult = productRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        }
        else {
            return new ArrayList<Product>();
        }
    }
}
