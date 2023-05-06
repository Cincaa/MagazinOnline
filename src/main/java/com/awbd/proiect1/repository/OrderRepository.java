package com.awbd.proiect1.repository;

import com.awbd.proiect1.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findById(Long id);
}
