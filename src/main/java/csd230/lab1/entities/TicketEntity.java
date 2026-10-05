package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity @DiscriminatorValue("TICKET")
public class TicketEntity extends ProductEntity {
    private String description;
    private String type;

    public TicketEntity() {}
    public TicketEntity(String description, double price, int copies, String type) {
        super(description, price, copies);
        this.description = description;
        this.type = type;
    }

    @Override public void sellItem() {
        System.out.println("Selling Ticket: " + description + " for $" + getPrice());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Ticket{desc='" + description + "', price=" + getPrice() + "}";
    }
}
