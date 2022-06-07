package com.belhard.service.dto.order;

import com.belhard.service.dto.user.UserDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private UserDto user;
    private OrderStatusDto status;
    private BigDecimal totalCost;
    private Date createTime;
    private Date updateTime;
    private List<OrderItemDto> orderItems;

    public enum OrderStatusDto {

        RESERVED("reserved"),
        CONFIRMED("confirmed"),
        CANCELED("canceled");

        private String title;

        OrderStatusDto(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
