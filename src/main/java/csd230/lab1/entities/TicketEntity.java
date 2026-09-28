package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("TICKET")
public class TicketEntity extends ProductEntity {
    private String description;

    @Column(name = "ticket_price")
    private double price;
    private String type;

    public TicketEntity() {}
    public TicketEntity(String description, double price, int copies, String type) {
        super(description, price, copies);
        this.description = description;
        this.price = price;
        this.type = type;
    }

    @Override public void sellItem() {
        System.out.println("Selling Ticket: " + description + " for $" + price);
    }
    @Override
    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String d) {
        this.description = d;
    }
    public void setPrice(double p) {
        this.price = p;
    }

    public String getType() {
        return type;
    }


    @Override public String toString() {
        return "Ticket{desc='" + description + "', price=" + price + "}";
    }
}
