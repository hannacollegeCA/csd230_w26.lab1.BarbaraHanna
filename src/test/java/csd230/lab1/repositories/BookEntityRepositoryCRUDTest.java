package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class BookEntityRepositoryCRUDTest {

    @Autowired
    private BookEntityRepository repo;

    @Test
    void testCRUD() {

        // CREATE
        BookEntity book = new BookEntity(
                "Test Title",
                19.99,
                5,
                "Test Author",
                "ABC123"
        );

        BookEntity saved = repo.save(book);
        assertThat(saved.getId()).isNotNull();

        // READ
        BookEntity found = repo.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getAuthor()).isEqualTo("Test Author");

        // UPDATE
        found.setPrice(29.99);
        repo.save(found);

        BookEntity updated = repo.findById(saved.getId()).orElse(null);
        assertThat(updated.getPrice()).isEqualTo(29.99);

        // DELETE
        repo.delete(updated);
        assertThat(repo.findById(saved.getId())).isEmpty();
    }
}
