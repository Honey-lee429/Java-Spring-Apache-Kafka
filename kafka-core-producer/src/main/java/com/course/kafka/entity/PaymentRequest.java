package com.course.kafka.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentRequest {

    private String paymentNumber;
    private int amount;
    private String currency;
    private String notes;
    private String transactionType;
}
