package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity @DiscriminatorValue("BOOK")
public class BookEntity extends ProductEntity {
    private String isbn;
    private String author;
    public BookEntity() {}
    public BookEntity(String title, double price, int copies, String author, String isbn) {
        super(title, price, copies);
        this.author = author;
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
    public void sellItem() {
        System.out.println("Selling Book: " + getTitle() + " by " + author);
    }

    @Override
    public String toString() {
        return "Book{author='" + author + "', " + super.toString() + "}";
    }
}
