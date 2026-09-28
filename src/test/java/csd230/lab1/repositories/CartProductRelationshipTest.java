package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.MagazineEntity;
import csd230.lab1.entities.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartProductRelationshipTest {

    @Autowired
    private CartEntityRepository cartRepo;

    @Autowired
    private ProductEntityRepository productRepo;

    @Test
    void testCartProductRelationship() {

        CartEntity cart = new CartEntity();
        cartRepo.save(cart);

        BookEntity book = new BookEntity(
                "Test Book",
                19.99,
                5,
                "Test Author",
                "ISBN123"
        );

        MagazineEntity mag = new MagazineEntity(
                "Tech Magazine",
                12.99,
                20,
                50,
                LocalDateTime.now()
        );

        // Add products to cart
        cart.addProduct(book);
        cart.addProduct(mag);

        cartRepo.save(cart);

        // Fetch cart again
        CartEntity found = cartRepo.findById(cart.getId()).orElseThrow();

        Set<ProductEntity> products = found.getProducts();

        assertThat(products).hasSize(2);

        // Check bidirectional link
        for (ProductEntity p : products) {
            assertThat(p.getCarts()).contains(found);
        }
    }
}
