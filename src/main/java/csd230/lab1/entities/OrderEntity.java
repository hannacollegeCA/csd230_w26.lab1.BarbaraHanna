package csd230.lab1.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a completed purchase order in the system.
 * Lab 2 Requirement: This entity stores the snapshot of the cart at checkout.
 */

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto-generated order I

    private double totalAmount;

    private LocalDateTime orderDate;

    /**
     * Many-to-Many relationship with ProductEntity.
     * Lab 2 Requirement: An order contains multiple products.
     * Uses a join table "order_products" to store the relationship.
     */

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"), // FK to this order
            inverseJoinColumns = @JoinColumn(name = "product_id") // FK to purchased products
    )
    private Set<ProductEntity> products = new LinkedHashSet<>();

    // Getters and setters
    public Long getId() { return id; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public Set<ProductEntity> getProducts() { return products; }
}
