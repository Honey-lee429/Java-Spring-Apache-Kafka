package com.course.kafka.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PaymentRequest {
// we don't longer need the entire information that PaymentRequest on producer provides for chache key
    // we must carefully assess, what is the shortest combination possible to produce unique value.
    private String paymentNumber;
    private int amount;
    //private String currency;
    //private String notes;
    private String transactionType;
}
