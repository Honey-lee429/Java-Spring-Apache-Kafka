package com.course.kafka.entity;
//this class is the messsage thet will be send to kafka

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarLocation {
    private String carId;
    private long timestamp;
    private int distance;


}
