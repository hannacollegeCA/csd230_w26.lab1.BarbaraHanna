package csd230.lab1.controllers;

import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartEntityRepository cartRepository;
    private final BookEntityRepository bookRepository;
    private final ProductEntityRepository productRepository;
    private final OrderEntityRepository orderRepository;

    public CartController(CartEntityRepository cartRepository,
                          BookEntityRepository bookRepository,
                          ProductEntityRepository productRepository,
                          OrderEntityRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String viewCart(Model model) {
        CartEntity cart = cartRepository.findById(1L).orElse(null);
        model.addAttribute("cart", cart);
        return "cartDetails";
    }

    @GetMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId) {
        CartEntity cart = cartRepository.findById(1L).orElse(null);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (cart != null && book != null) {
            cart.addProduct(book);
            cartRepository.save(cart);
        }

        return "redirect:/cart";
    }

    @GetMapping("/remove/{bookId}")
    public String removeFromCart(@PathVariable Long bookId) {
        CartEntity cart = cartRepository.findById(1L).orElse(null);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (cart != null && book != null) {
            cart.removeProduct(book);
            cartRepository.save(cart);
        }

        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Model model) {

        CartEntity cart = cartRepository.findById(1L).orElse(null);

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
