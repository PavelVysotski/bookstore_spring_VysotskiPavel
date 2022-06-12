package com.belhard.repository.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

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
