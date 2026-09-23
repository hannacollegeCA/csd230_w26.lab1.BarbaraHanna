package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {
    // Derived queries
    List<BookEntity> findByIsbn(String isbn);
    List<BookEntity> findByTitle(String title);
    List<BookEntity> findByTitleLike(String titlePattern);
}
