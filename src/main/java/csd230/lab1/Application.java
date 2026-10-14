package csd230.lab1;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.UserEntity;
import csd230.lab1.repositories.BookEntityRepository;
import csd230.lab1.repositories.CartEntityRepository;
import csd230.lab1.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication // Marks this class as the main Spring Boot application
public class Application implements CommandLineRunner {

    private final BookEntityRepository bookRepository;
    private final CartEntityRepository cartRepository;
    private final UserEntityRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // Repository used to interact with the database

    public Application(BookEntityRepository bookRepository, CartEntityRepository cartRepository, UserEntityRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        // Inject the repository through the constructor
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

        // Create default cart
        if (!cartRepository.existsById(1L)) {
            CartEntity cart = new CartEntity();
            cartRepository.save(cart);
        }
        // Create admin user
        UserEntity admin = new UserEntity(
                "admin",
                passwordEncoder.encode("admin"),
                "ADMIN"
        );
        userRepository.save(admin);

        // Only create it if it doesn't exist yet
        if (!cartRepository.existsById(1L)) {
            CartEntity cart = new CartEntity();
            cartRepository.save(cart); // Save the empty cart
        }

        // Create normal user
        UserEntity user = new UserEntity(
                "user",
                passwordEncoder.encode("user"),
                "USER"
        );
        userRepository.save(user);

    }

}
