package com.awbd.proiect1.repository;

import com.awbd.proiect1.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Optional<Product> findById(Long id);

    List<Product> findAll();

}
