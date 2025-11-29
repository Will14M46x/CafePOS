package com.cafepos.domain;

import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findByID(long id);
}
