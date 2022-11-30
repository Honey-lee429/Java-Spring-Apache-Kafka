package com.course.kafka.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodOrder {

    private BigDecimal amount;
    private String item;

}
