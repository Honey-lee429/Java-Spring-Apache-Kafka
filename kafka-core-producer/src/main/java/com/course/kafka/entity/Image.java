package com.course.kafka.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Image {
    private String name;
    private long size;
    private String type;


}
