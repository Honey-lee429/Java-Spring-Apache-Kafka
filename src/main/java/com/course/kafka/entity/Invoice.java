package com.course.kafka.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Invoice {
    private String invoiceNumber;
    private int amount;
    private String currency;
}
