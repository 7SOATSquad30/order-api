package br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ProductEntityTest {

    private static final Long CATEGORY_ID = 1L;
    private static final String CATEGORY_NAME = "Snacks";
    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "Burger";
    private static final String PRODUCT_DESCRIPTION = "Delicious cheeseburger";
    private static final Double PRODUCT_PRICE = 9.99;
    private static final String PRODUCT_IMG_URL = "http://example.com/burger.png";

    @Test
    void prePersistShouldSetCreatedAt() {
        // Arrange
        ProductEntity product = new ProductEntity();

        // Act
        product.prePersist();

        // Assert
        assertNotNull(product.getCreatedAt(), "CreatedAt should be set during prePersist");
    }

    @Test
    void preUpdateShouldSetUpdatedAt() {
        // Arrange
        ProductEntity product = new ProductEntity();

        // Act
        product.preUpdate();

        // Assert
        assertNotNull(product.getUpdatedAt(), "UpdatedAt should be set during preUpdate");
    }

    @Test
    void preRemoveShouldSetDeletedAt() {
        // Arrange
        ProductEntity product = new ProductEntity();

        // Act
        product.preRemove();

        // Assert
        assertNotNull(product.getDeletedAt(), "DeletedAt should be set during preRemove");
    }

    @Test
    void constructorShouldCreateProductEntityWithCorrectId() {
        // Arrange
        CategoryEntity category = new CategoryEntity(CATEGORY_ID, CATEGORY_NAME);

        // Act
        ProductEntity entity =
                new ProductEntity(
                        PRODUCT_ID,
                        PRODUCT_NAME,
                        PRODUCT_DESCRIPTION,
                        PRODUCT_PRICE,
                        PRODUCT_IMG_URL,
                        category);

        // Assert
        assertThat(entity.getId()).isEqualTo(PRODUCT_ID);
    }

    @Test
    void constructorShouldCreateProductEntityWithCorrectName() {
        // Arrange
        CategoryEntity category = new CategoryEntity(CATEGORY_ID, CATEGORY_NAME);

        // Act
        ProductEntity entity =
                new ProductEntity(
                        PRODUCT_ID,
                        PRODUCT_NAME,
                        PRODUCT_DESCRIPTION,
                        PRODUCT_PRICE,
                        PRODUCT_IMG_URL,
                        category);

        // Assert
        assertThat(entity.getName()).isEqualTo(PRODUCT_NAME);
    }

    @Test
    void toDomainEntityShouldReturnProduct() {
        // Arrange
        CategoryEntity category = new CategoryEntity(CATEGORY_ID, CATEGORY_NAME);
        ProductEntity product =
                new ProductEntity(
                        PRODUCT_ID,
                        PRODUCT_NAME,
                        PRODUCT_DESCRIPTION,
                        PRODUCT_PRICE,
                        PRODUCT_IMG_URL,
                        category);

        // Act
        var domainProduct = product.toDomainEntity();

        // Assert
        assertThat(domainProduct.getName()).isEqualTo(product.getName());
    }
}
