package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PUBLICATION")

public abstract class PublicationEntity extends ProductEntity {

    private String author;
    private int pages;

    public PublicationEntity() {}

    public PublicationEntity(String title, double price, int copies) {
        super(title, price, copies);
    }

    public String getAuthor() {
        return author;
    }

    public  void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void  setPages(int pages) {
        this.pages = pages;
    }

}
