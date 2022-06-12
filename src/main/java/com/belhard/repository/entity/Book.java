package com.belhard.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "cover")
    @Enumerated(EnumType.STRING)
    private TypeCover typeCover;

    @Column(name = "price")
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
