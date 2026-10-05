package csd230.lab1.entities;

import jakarta.persistence.*;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@DiscriminatorValue("MAGAZINE")
public class MagazineEntity extends PublicationEntity {

    private String publisher;
    private int issueNumber;


    public MagazineEntity() {}

    public MagazineEntity(String title, double price, int copies, String author, int pages,
                          String publisher, int issueNumber) {

        super(title, price, copies);
        setAuthor(author);
        setPages(pages);

        this.publisher = publisher;
        this.issueNumber = issueNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Override
    public void sellItem() {
        if (getCopies() > 0) {
            setCopies(getCopies() - 1);
            System.out.println("Sold Magazine: " + getTitle() + " (Issue " + issueNumber + ")");
        } else {
            System.out.println("Cannot sell '" + getTitle() + "'. Out of stock.");
        }
    }

    @Override
    public String toString() {
        return "Magazine{publisher='" + publisher + "', issue=" + issueNumber + ", " + super.toString() + "}";
    }


}
