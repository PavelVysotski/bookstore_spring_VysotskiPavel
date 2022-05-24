package com.belhard.dao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {

    private Long id;
    private Long userId;
    private OrderStatus status;
    private BigDecimal totalCost;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<OrderItem> orderItems;

    public enum OrderStatus {

        RESERVED("reserved"),
        CONFIRMED("confirmed"),
        CANCELED("canceled");

        private String title;

        OrderStatus(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
