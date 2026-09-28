package csd230.lab1.entities;

import csd230.lab1.pojos.SaleableItem;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)

public abstract class ProductEntity implements Serializable, SaleableItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = false)
    private double price;

    private int copies;

    @ManyToMany(mappedBy = "products")
    private Set<CartEntity> carts = new HashSet<>();

    public ProductEntity() {}

    public ProductEntity(String title, double price, int copies) {
        this.title = title;
        this.price = price;
        this.copies = copies;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getCopies() {
        return copies;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "ProductEntity{id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", copies=" + copies + "}";
    }
}


