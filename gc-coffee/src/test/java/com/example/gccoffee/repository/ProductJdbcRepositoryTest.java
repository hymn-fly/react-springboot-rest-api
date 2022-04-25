package com.example.gccoffee.repository;

import com.example.gccoffee.domain.Category;
import com.example.gccoffee.domain.Product;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Transactional
class ProductJdbcRepositoryTest {

    @Autowired
    ProductRepository repository;

    private final Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);
    private final Product newProduct2 = new Product(UUID.randomUUID(), "new-product2", Category.COFFEE_BEAN_PACKAGE, 1000L);
    private final Product newProduct3 = new Product(UUID.randomUUID(), "new-product3", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @BeforeEach
    void insertData(){
        repository.insert(newProduct);
        repository.insert(newProduct2);
    }

    @Test
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert() {
        repository.insert(newProduct3);

        val all = repository.findAll();

        assertThat(all).contains(newProduct3);
    }

    @Test
    @DisplayName("상품을 이름으로 조회할 수 있다")
    void findByNameTest() {
        val product = repository.findByName(newProduct.getName());

        assertThat(product.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("상품을 아이디로 조회할 수 있다")
    void findByIdTest() {
        val product = repository.findById(newProduct.getId());

        assertThat(product.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("상품을 카테고리로 조회할 수 있다.")
    void findByCategory() {
        repository.insert(newProduct3);

        val products = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);

        assertThat(products).containsExactlyInAnyOrder(newProduct, newProduct2, newProduct3);
    }

    @Test
    @DisplayName("상품을 수정할 수 있다")
    void updateTest() {
        newProduct.setName("updated-product");

        repository.update(newProduct);
        val maybeProduct = repository.findById(newProduct.getId());

        assertThat(maybeProduct.isEmpty()).isFalse();
        assertThat(maybeProduct.get()).extracting(Product::getName).isEqualTo("updated-product");
    }

    @Test
    @DisplayName("상품을 전체 삭제한다")
    void testDeleteAll() {
        repository.deleteAll();

        val all = repository.findAll();
        assertThat(all).hasSize(0);
    }
}