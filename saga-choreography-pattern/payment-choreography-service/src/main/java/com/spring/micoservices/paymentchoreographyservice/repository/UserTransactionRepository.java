package com.spring.micoservices.paymentchoreographyservice.repository;

import com.spring.micoservices.paymentchoreographyservice.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, UUID> {

}
