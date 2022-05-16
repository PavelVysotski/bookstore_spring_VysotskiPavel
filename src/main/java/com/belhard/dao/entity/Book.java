package com.belhard.dao.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Book {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private TypeCover typeCover;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public TypeCover getTypeCover() {
        return typeCover;
    }

    public void setTypeCover(TypeCover typeCover) {
        this.typeCover = typeCover;
    }

    public BigDecimal getPrice() {
        return price.setScale(2, RoundingMode.UP);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id)
                && Objects.equals(isbn, book.isbn)
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && typeCover == book.typeCover
                && Objects.equals(price, book.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, author, typeCover, price);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", typeCover=" + typeCover +
                ", price=" + price +
                '}';
    }

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
