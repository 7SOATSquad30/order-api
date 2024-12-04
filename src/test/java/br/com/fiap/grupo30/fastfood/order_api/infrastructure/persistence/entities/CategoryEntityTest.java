package br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CategoryEntityTest {

    private static final Long CATEGORY_ID = 1L;
    private static final String CATEGORY_NAME = "Snacks";

    @Test
    void prePersistShouldSetCreatedAt() {
        // Arrange
        CategoryEntity category = new CategoryEntity();

        // Act
        category.prePersist();

        // Assert
        assertNotNull(category.getCreatedAt(), "CreatedAt should be set during prePersist");
    }

    @Test
    void preUpdateShouldSetUpdatedAt() {
        // Arrange
        CategoryEntity category = new CategoryEntity();

        // Act
        category.preUpdate();

        // Assert
        assertNotNull(category.getUpdatedAt(), "UpdatedAt should be set during preUpdate");
    }

    @Test
    void preRemoveShouldSetDeletedAt() {
        // Arrange
        CategoryEntity category = new CategoryEntity();

        // Act
        category.preRemove();

        // Assert
        assertNotNull(category.getDeletedAt(), "DeletedAt should be set during preRemove");
    }

    @Test
    void constructorShouldCreateCategoryEntityWithCorrectId() {
        // Act
        CategoryEntity category = new CategoryEntity(CATEGORY_ID, CATEGORY_NAME);

        // Assert
        assertThat(category.getId()).isEqualTo(CATEGORY_ID);
    }

    @Test
    void constructorShouldCreateCategoryEntityWithCorrectName() {
        // Act
        CategoryEntity category = new CategoryEntity(CATEGORY_ID, CATEGORY_NAME);

        // Assert
        assertThat(category.getName()).isEqualTo(CATEGORY_NAME);
    }

    @Test
    void toDomainEntityShouldReturnCategory() {
        // Arrange
        CategoryEntity category = new CategoryEntity(CATEGORY_ID, CATEGORY_NAME);

        // Act
        var domainCategory = category.toDomainEntity();

        // Assert
        assertThat(domainCategory.getName()).isEqualTo(category.getName());
    }
}
