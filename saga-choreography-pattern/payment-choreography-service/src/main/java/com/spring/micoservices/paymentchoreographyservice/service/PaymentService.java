package com.spring.micoservices.paymentchoreographyservice.service;

import com.spring.micoservices.paymentchoreographyservice.entity.UserTransaction;
import com.spring.micoservices.paymentchoreographyservice.repository.UserBalanceRepository;
import com.spring.micoservices.paymentchoreographyservice.repository.UserTransactionRepository;
import com.spring.microservices.choreography.dto.PaymentDto;
import com.spring.microservices.choreography.dto.PurchaseOrderDto;
import com.spring.microservices.choreography.events.order.OrderEvent;
import com.spring.microservices.choreography.events.payment.PaymentEvent;
import com.spring.microservices.choreography.events.payment.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        PurchaseOrderDto purchaseOrder = orderEvent.getPurchaseOrder();
        PaymentDto paymentDto = PaymentDto.of(
                purchaseOrder.getOrderId(),
                purchaseOrder.getUserId(),
                purchaseOrder.getPrice()
        );
        return this.userBalanceRepository.findById(purchaseOrder.getUserId())
                .filter(userBalance -> userBalance.getBalance() >= purchaseOrder.getPrice())
                .map(userBalance -> {
                    userBalance.setBalance(userBalance.getBalance() - purchaseOrder.getPrice());
                    this.userTransactionRepository.save(UserTransaction.of(purchaseOrder.getOrderId(), purchaseOrder.getUserId(), purchaseOrder.getPrice()));
                    return new PaymentEvent(paymentDto, PaymentStatus.RESERVED);
                })
                .orElse(new PaymentEvent(paymentDto, PaymentStatus.REJECTED));
    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        this.userTransactionRepository.findById(orderEvent.getPurchaseOrder().getOrderId())
                .ifPresent(userTransaction -> {
                    this.userTransactionRepository.delete(userTransaction);
                    this.userBalanceRepository.findById(userTransaction.getUserId())
                            .ifPresent(userBalance -> userBalance.setBalance(userBalance.getBalance() + userTransaction.getAmount()));
                });
    }
}
