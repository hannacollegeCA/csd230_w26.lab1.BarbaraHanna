package csd230.lab1.entities;

import csd230.lab1.pojos.SaleableItem;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type")
public abstract class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private double price;
    private int copies;

    @ManyToMany(mappedBy = "products")
    @Access(AccessType.PROPERTY)
    private Set<CartEntity> carts = new LinkedHashSet<>();

    public ProductEntity() {
    }

    public ProductEntity(String title, double price, int copies) {
        this.title = title;
        this.price = price;
        this.copies = copies;
    }

    public abstract void sellItem();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public Set<CartEntity> getCarts() {
        return carts;
    }

    public void setCarts(Set<CartEntity> carts) {
        this.carts = carts;
    }
}


