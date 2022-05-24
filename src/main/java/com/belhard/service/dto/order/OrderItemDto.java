package com.belhard.service.dto.order;

import com.belhard.service.dto.book.BookDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {

    private Long id;
    private BookDto book;
    private Integer quantity;
    private BigDecimal price;

}
