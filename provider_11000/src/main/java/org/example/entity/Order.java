package org.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Order implements Serializable {
    private Integer userId;
    private Integer id;
    private final LocalDateTime createTime=LocalDateTime.now();
    private LocalDateTime updateTime;
}
