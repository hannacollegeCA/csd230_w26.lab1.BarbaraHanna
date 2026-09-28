package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomQueryTest {

    @Autowired
    private ProductEntityRepository repo;

    @Test
    void testPriceRangeQuery() {

        repo.save(new BookEntity("Cheap Book", 10.00, 5, "A", "C1"));
        repo.save(new BookEntity("Mid Book", 25.00, 5, "B", "C2"));
        repo.save(new BookEntity("Expensive Book", 50.00, 5, "C", "C3"));

        List<ProductEntity> result = repo.findProductsInPriceRange(20, 40);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPrice()).isEqualTo(25.00);
    }
}
