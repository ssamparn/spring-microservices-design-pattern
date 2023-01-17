package com.spring.microservices.choreography.events.payment;

import com.spring.microservices.choreography.dto.PaymentDto;
import com.spring.microservices.choreography.events.Event;

import java.util.Date;
import java.util.UUID;

public class PaymentEvent implements Event {

    private final UUID eventId = UUID.randomUUID();
    private final Date date = new Date();

    private PaymentDto payment;
    private PaymentStatus paymentStatus;

    public PaymentEvent() {
    }

    public PaymentEvent(PaymentDto payment, PaymentStatus paymentStatus) {
        this.payment = payment;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    public PaymentDto getPayment() {
        return payment;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
}
