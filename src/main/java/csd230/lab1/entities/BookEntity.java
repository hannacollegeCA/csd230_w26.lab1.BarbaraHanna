package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity @DiscriminatorValue("BOOK")
public class BookEntity extends PublicationEntity {
    private String isbn;
    private String author;
    public BookEntity() {}
    public BookEntity(String t, double p, int c, String a) {
        super(t, p, c);
        this.author = a;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String a) {
        this.author = a;
    }

    public String getIsbn() {
        return  isbn;
    }

    public void  setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{author='" + author + "', " + super.toString() + "}";
    }
}
