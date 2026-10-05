package csd230.lab1.entities;

import jakarta.persistence.*;


@Entity
@DiscriminatorValue("BOOK")
public class BookEntity extends PublicationEntity {
    private String isbn;
    private String genre;

    public BookEntity() {}
    public BookEntity(String title, double price, int copies, String author, String isbn) {
        super(title, price, copies);
        setAuthor(author);
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public void sellItem() {
        if (getCopies() > 0) {
            setCopies(getCopies() - 1);
            System.out.println("Sold Book: " + getTitle() + " by " + getAuthor());
        } else {
            System.out.println("Cannot sell '" + getTitle() + "'. Out of stock.");
        }
    }

    @Override
    public String toString() {
        return "Book{isbn='" + isbn + "', author='" + getAuthor() + "', " + super.toString() + "}";
    }
}
