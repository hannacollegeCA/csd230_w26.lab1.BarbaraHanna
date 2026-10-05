package csd230.lab1.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@DiscriminatorValue("DISCMAG")
public class DiscMagEntity extends MagazineEntity {
    private boolean hasDisc;

    public DiscMagEntity() {
    }

    public DiscMagEntity(String title, double price, int copies,
                         String author, int pages,
                         String publisher, int issueNumber,
                         boolean hasDisc) {


        super(title, price, copies, author, pages, publisher, issueNumber);
        this.hasDisc = hasDisc;
    }

    public boolean isHasDisc() {
        return hasDisc;
    }

    public void setHasDisc(boolean h) {
        this.hasDisc = h;
    }

    @Override
    public void sellItem() {
        if (getCopies() > 0) {
            setCopies(getCopies() - 1);
            System.out.println("Sold Disc Magazine: " + getTitle() +
                    " (Issue " + getIssueNumber() + ") — Disc included: " + hasDisc);
        } else {
            System.out.println("Cannot sell '" + getTitle() + "'. Out of stock.");
        }
    }

    @Override
    public String toString() {
        return "DiscMag{hasDisc=" + hasDisc + ", " + super.toString() + "}";
    }
}
