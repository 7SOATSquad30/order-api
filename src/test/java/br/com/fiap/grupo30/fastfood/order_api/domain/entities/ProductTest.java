package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.order_api.utils.ProductHelper;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void shouldCreateProductWithNullId() {
        // Act
        Product product = ProductHelper.createDefaultProduct();

        // Assert
        assertThat(product.getId()).isNull();
    }

    @Test
    void shouldCreateProductWithName() {
        // Act
        Product product = ProductHelper.createDefaultProduct();

        // Assert
        assertThat(product.getName()).isEqualTo("Burger");
    }

    @Test
    void shouldCreateProductWithDescription() {
        // Act
        Product product = ProductHelper.createDefaultProduct();

        // Assert
        assertThat(product.getDescription()).isEqualTo("Delicious burger");
    }

    @Test
    void shouldCreateProductWithPrice() {
        // Act
        Product product = ProductHelper.createDefaultProduct();

        // Assert
        assertThat(product.getPrice()).isEqualTo(12.99);
    }

    @Test
    void shouldCreateProductWithImgUrl() {
        // Act
        Product product = ProductHelper.createDefaultProduct();

        // Assert
        assertThat(product.getImgUrl()).isEqualTo("http://example.com/burger.png");
    }

    @Test
    void shouldCreateProductWithCategory() {
        // Arrange
        Category category = ProductHelper.createDefaultCategory();

        // Act
        Product product = ProductHelper.createDefaultProduct();

        // Assert
        assertThat(product.getCategory()).isEqualTo(category);
    }

    @Test
    void shouldConvertToDTOWithName() {
        // Arrange
        Product product = ProductHelper.createDefaultProductWithId(1L);

        // Act
        ProductDTO productDTO = product.toDTO();

        // Assert
        assertThat(productDTO.getName()).isEqualTo("Burger");
    }

    @Test
    void shouldConvertToPersistenceWithId() {
        // Arrange
        Product product = ProductHelper.createDefaultProductWithId(1L);

        // Act
        ProductEntity productEntity = product.toPersistence();

        // Assert
        assertThat(productEntity.getId()).isEqualTo(1L);
    }

    @Test
    void shouldCompareEqualityWhenIdsMatch() {
        // Arrange
        Product product1 = ProductHelper.createDefaultProductWithId(1L);
        Product product2 =
                new Product(
                        1L,
                        "French Fries",
                        "Delicious french fries",
                        15.99,
                        "http://example.com/french-fries.png",
                        new Category(2L, "Sides"));

        // Act & Assert
        assertThat(product1).isEqualTo(product2);
    }

    @Test
    void shouldNotCompareEqualityWhenIdsDiffer() {
        // Arrange
        Product product1 = ProductHelper.createDefaultProductWithId(1L);
        Product product2 =
                new Product(
                        2L,
                        "Burger",
                        "Delicious burger",
                        12.99,
                        "http://example.com/burger.png",
                        new Category(1L, "Snacks"));

        // Act & Assert
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void shouldHaveSameHashCodeForSameId() {
        // Arrange
        Product product1 = ProductHelper.createDefaultProductWithId(1L);
        Product product2 = ProductHelper.createDefaultProductWithId(1L);

        // Act & Assert
        assertThat(product1.hashCode()).hasSameHashCodeAs(product2.hashCode());
    }

    @Test
    void shouldHaveDifferentHashCodeForDifferentIds() {
        // Arrange
        Product product1 = ProductHelper.createDefaultProductWithId(1L);
        Product product2 = ProductHelper.createDefaultProductWithId(2L);

        // Act & Assert
        assertThat(product1.hashCode()).isNotEqualTo(product2.hashCode());
    }

    @Test
    void shouldHaveHashCodeForNullId() {
        // Arrange
        Product product = ProductHelper.createDefaultProduct();

        // Act & Assert
        assertThat(product.hashCode()).isEqualTo(Objects.hash((Object) null));
    }
}
