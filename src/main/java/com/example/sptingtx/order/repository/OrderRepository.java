package com.example.sptingtx.order.repository;

import com.example.sptingtx.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
