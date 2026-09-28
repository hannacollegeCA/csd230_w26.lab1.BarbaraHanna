package csd230.lab1;

import com.github.javafaker.Faker;
import com.github.javafaker.Commerce;
import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final ProductEntityRepository productRepo;
    private final BookEntityRepository bookRepo;
    private final MagazineEntityRepository magazineRepo;
    private final DiscMagEntityRepository discRepo;
    private final TicketEntityRepository ticketRepo;
    private final CartEntityRepository cartRepo;

    public Application(ProductEntityRepository productRepo,
                       BookEntityRepository bookRepo,
                       MagazineEntityRepository magazineRepo,
                       DiscMagEntityRepository discRepo,
                       TicketEntityRepository ticketRepo,
                       CartEntityRepository cartRepo) {

        this.productRepo = productRepo;
        this.bookRepo = bookRepo;
        this.magazineRepo = magazineRepo;
        this.discRepo = discRepo;
        this.ticketRepo = ticketRepo;
        this.cartRepo = cartRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {

        Faker faker = new Faker();
        Commerce commerce = faker.commerce();

        // CREATE
        BookEntity book = new BookEntity(
                faker.book().title(),
                Double.parseDouble(commerce.price()),
                10,
                faker.book().author(),
                "ISBN-" + faker.number().digits(5)
        );

        MagazineEntity magazine = new MagazineEntity(
                faker.lorem().word() + " Magazine",
                12.99,
                20,
                50
        );

        DiscMagEntity disc = new DiscMagEntity(
                "Science Disc",
                14.99,
                3,
                true
        );

        TicketEntity ticket = new TicketEntity(
                "Concert Ticket",
                59.99,
                100,
                "VIP"
        );

        productRepo.save(book);
        productRepo.save(magazine);
        productRepo.save(disc);
        productRepo.save(ticket);

        // CART
        CartEntity cart = new CartEntity();
        cart.addProduct(book);
        cart.addProduct(magazine);
        cart.addProduct(ticket);
        cartRepo.save(cart);

        // READ
        System.out.println("=== ALL PRODUCTS ===");
        productRepo.findAll().forEach(System.out::println);

        System.out.println("=== ALL CARTS ===");
        cartRepo.findAll().forEach(c -> {
            System.out.println(c);
            c.getProducts().forEach(System.out::println);
        });

        // DERIVED QUERY
        System.out.println("=== FIND BY ISBN ===");
        bookRepo.findByIsbn(book.getIsbn()).forEach(System.out::println);

        // LIKE QUERY
        System.out.println("=== FIND BY TITLE LIKE ===");
        bookRepo.findByTitleLike("%" + book.getTitle().substring(0, 3) + "%")
                .forEach(System.out::println);

        // CUSTOM JPQL
        System.out.println("=== PRODUCTS IN PRICE RANGE 10–50 ===");
        productRepo.findProductsInPriceRange(10, 50)
                .forEach(System.out::println);

        // UPDATE
        book.setPrice(99.99);
        productRepo.save(book);

        // DELETE
        productRepo.delete(ticket);

        System.out.println("=== AFTER UPDATE & DELETE ===");
        productRepo.findAll().forEach(System.out::println);
    }
}
