package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookEntityDerivedQueryTest {

    @Autowired
    private BookEntityRepository repo;

    @Test
    void testFindByIsbn() {
        BookEntity b1 = new BookEntity("Java Basics", 29.99, 10, "Author A", "ISBN123");
        BookEntity b2 = new BookEntity("Spring Boot", 39.99, 5, "Author B", "ISBN999");

        repo.save(b1);
        repo.save(b2);

        List<BookEntity> result = repo.findByIsbn("ISBN123");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Java Basics");
    }

    @Test
    void testFindByTitleLike() {
        BookEntity b1 = new BookEntity("Java Programming", 29.99, 10, "Author A", "A1");
        BookEntity b2 = new BookEntity("Advanced Java", 39.99, 5, "Author B", "A2");

        repo.save(b1);
        repo.save(b2);

        List<BookEntity> result = repo.findByTitleLike("%Java%");

        assertThat(result).hasSize(2);
    }
}

