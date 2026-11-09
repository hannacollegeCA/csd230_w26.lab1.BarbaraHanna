package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book REST API")
@RestController
@RequestMapping("/api/rest/books")
public class BookRestController {

    private final BookEntityRepository repository;

    public BookRestController(BookEntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<BookEntity> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public BookEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PostMapping
    public BookEntity create(@RequestBody BookEntity book) {
        return repository.save(book);
    }

    @PutMapping("/{id}")
    public BookEntity update(
            @RequestBody BookEntity newBook,
            @PathVariable Long id) {

        return repository.findById(id)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                    book.setPrice(newBook.getPrice());
                    book.setCopies(newBook.getCopies());
                    book.setIsbn(newBook.getIsbn());

                    return repository.save(book);
                })
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
