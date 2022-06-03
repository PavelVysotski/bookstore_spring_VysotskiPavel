package com.belhard.dao.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private TypeCover typeCover;
    private BigDecimal price;

    public enum TypeCover {

        HARD("hard"),
        SOFT("soft");

        private final String title;

        TypeCover(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
