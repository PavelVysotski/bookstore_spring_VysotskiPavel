package com.belhard.service.dto.book;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDto {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private TypeCoverDto typeCover;
    private BigDecimal price;

    public enum TypeCoverDto {

        HARD("hard"),
        SOFT("soft");

        private final String title;

        TypeCoverDto(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
