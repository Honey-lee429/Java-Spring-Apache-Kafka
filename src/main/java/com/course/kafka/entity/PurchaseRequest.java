package com.course.kafka.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseRequest {
    private int id;
    private String prNumer;
    private int amount;
    private String currency;
}
