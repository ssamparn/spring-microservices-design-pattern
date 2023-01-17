package com.spring.micoservices.inventorychoreographyservice.repository;

import com.spring.micoservices.inventorychoreographyservice.entity.OrderInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInventoryRepository extends JpaRepository<OrderInventory, Integer> {
}
