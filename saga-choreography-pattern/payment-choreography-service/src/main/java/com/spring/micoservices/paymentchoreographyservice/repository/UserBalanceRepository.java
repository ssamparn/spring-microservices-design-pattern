package com.spring.micoservices.paymentchoreographyservice.repository;

import com.spring.micoservices.paymentchoreographyservice.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer> {
}
