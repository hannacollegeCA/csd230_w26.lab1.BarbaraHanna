package csd230.lab1;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.CartEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Marks this class as the main Spring Boot application
public class Application implements CommandLineRunner {

    private final BookEntityRepository bookRepository;
    private final CartEntityRepository cartRepository;
    // Repository used to interact with the database

    public Application(BookEntityRepository bookRepository, CartEntityRepository cartRepository) {
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository; // Inject the repository through the constructor
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    } // Starts the Spring Boot application and the embedded web server (Tomcat)

    @Override
    public void run(String... args) {

        // Seed book data (Lecture 2.5)
        // This runs only once when the application starts
        if (bookRepository.count() == 0) {

            // First example book
            BookEntity first = new BookEntity();
            first.setTitle("Spring MVC Basics");
            first.setPrice(29.99);
            first.setCopies(5);
            first.setAuthor("Course Example");
            bookRepository.save(first);// Save to the database

            // Second example book
            BookEntity second = new BookEntity();
            second.setTitle("Thymeleaf in Practice");
            second.setPrice(34.99);
            second.setCopies(4);
            second.setAuthor("Course Example");
            bookRepository.save(second);// Save to the database

        }

        // Only create it if it doesn't exist yet
        if (!cartRepository.existsById(1L)) {
            CartEntity cart = new CartEntity();
            cartRepository.save(cart); // Save the empty cart
        }
    }
}
