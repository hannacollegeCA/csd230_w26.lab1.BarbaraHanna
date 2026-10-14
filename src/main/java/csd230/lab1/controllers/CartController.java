package csd230.lab1.controllers;

import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartEntityRepository cartRepository;
    private final BookEntityRepository bookRepository;
    private final ProductEntityRepository productRepository;
    private final OrderEntityRepository orderRepository;
    private final UserEntityRepository userRepository;

    public CartController(CartEntityRepository cartRepository,
                          BookEntityRepository bookRepository,
                          ProductEntityRepository productRepository,
                          OrderEntityRepository orderRepository,
                          UserEntityRepository userRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    /**
     * Returns the cart belonging to the logged-in user.
     * If the user does not have a cart yet, one is created automatically.
     */
    private CartEntity getCartForCurrentUser(Principal principal) {

        // 1. Get username from Spring Security
        String username = principal.getName();

        // 2. Find the UserEntity in the database
        UserEntity user = userRepository.findByUsername(username);

        // 3. Try to find an existing cart for this user
        CartEntity cart = cartRepository.findByUser(user);

        // 4. If no cart exists, create one and link it to the user
        if (cart == null) {
            cart = new CartEntity();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        return cart;
    }


    /**
     * Shows the cart for the logged-in user.
     */
    @GetMapping
    public String viewCart(Model model, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        model.addAttribute("cart", cart);
        return "cartDetails";
    }

    /**
     * Adds a book to the user's cart.
     */
    @GetMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            cart.addProduct(book);
            cartRepository.save(cart);
        }

        return "redirect:/cart";
    }


    /**
     * Removes a book from the user's cart.
     */
    @GetMapping("/remove/{bookId}")
    public String removeFromCart(@PathVariable Long bookId, Principal principal) {
        CartEntity cart = getCartForCurrentUser(principal);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            cart.removeProduct(book);
            cartRepository.save(cart);
        }

        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Model model, Principal principal) {

        CartEntity cart = getCartForCurrentUser(principal);

        if (cart == null || cart.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;

        for (ProductEntity product : cart.getProducts()) {

            total += product.getPrice();

            // Decrement stock only for publications
            if (product instanceof PublicationEntity pub) {
                pub.setCopies(pub.getCopies() - 1);
                productRepository.save(pub);
            }

            order.getProducts().add(product);
        }

        order.setTotalAmount(total);

        // Clear cart
        cart.getProducts().clear();

        // Save everything
        orderRepository.save(order);
        cartRepository.save(cart);

        model.addAttribute("order", order);

        return "orderDetails";
    }
}
