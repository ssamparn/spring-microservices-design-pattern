package com.spring.micoservices.orderchoreographyservice.service;

import com.spring.micoservices.orderchoreographyservice.entity.PurchaseOrder;
import com.spring.micoservices.orderchoreographyservice.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderQueryService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public List<PurchaseOrder> getAll() {
        return this.purchaseOrderRepository.findAll();
    }
}
