package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books") // Base route for all book-related pages
public class BookController {

    @Autowired
    private BookEntityRepository bookRepository; // Repository to interact with the database

    @GetMapping
    public String getAllBooks(Model model) {
        // Load all books from the database and send them to the bookList.html page
        model.addAttribute("books", bookRepository.findAll());
        return "bookList";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable Long id, Model model) {
        // Find a specific book by its ID and show its details
        BookEntity book = bookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "bookDetails";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        // Send an empty BookEntity to the form so the user can fill it out
        model.addAttribute("book", new BookEntity());
        return "addBook";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute BookEntity book) {
        // Save the new book submitted from the form
        bookRepository.save(book);
        return "redirect:/books";// After saving, go back to the list
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        // Load the existing book so the form can show its current data
        BookEntity book = bookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "editBook";
    }

    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute BookEntity updatedBook) {
        // Update the book with the new values submitted from the form
        BookEntity existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook != null) {
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setCopies(updatedBook.getCopies());
            bookRepository.save(existingBook);
        }
        return "redirect:/books"; // Go back to the list after updating
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        // Delete the book by its ID
        bookRepository.deleteById(id);
        return "redirect:/books"; // Refresh the list after deletion
    }
}
