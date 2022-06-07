package com.belhard.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateTime;

    @Transient
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
