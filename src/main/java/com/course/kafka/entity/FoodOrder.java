package com.course.kafka.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodOrder {

    private int amount;
    private String item;

}
