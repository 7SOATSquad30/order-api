import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.grupo30.fastfood.products_api.infrastructure.persistence.entities.CategoryEntity;
import br.com.fiap.grupo30.fastfood.products_api.presentation.presenters.dto.CategoryDTO;
import org.junit.jupiter.api.Test;

class CategoryTest {

    private static final String CATEGORY_NAME = "Drinks";

    @Test
    void shouldCreateCategoryWithNullId() {
        // Act
        Category category = Category.create(CATEGORY_NAME);

        // Assert
        assertThat(category.getId()).isNull();
    }

    @Test
    void shouldCreateCategoryWithCorrectName() {
        // Act
        Category category = Category.create(CATEGORY_NAME);

        // Assert
        assertThat(category.getName()).isEqualTo(CATEGORY_NAME);
    }

    @Test
    void shouldConvertToDTOWithCorrectName() {
        // Arrange
        Category category = new Category(1L, CATEGORY_NAME);

        // Act
        CategoryDTO dto = category.toDTO();

        // Assert
        assertThat(dto.getName()).isEqualTo(category.getName());
    }

    @Test
    void shouldConvertToPersistenceEntityWithCorrectId() {
        // Arrange
        Category category = new Category(1L, CATEGORY_NAME);

        // Act
        CategoryEntity entity = category.toPersistence();

        // Assert
        assertThat(entity.getId()).isEqualTo(category.getId());
    }

    @Test
    void shouldConvertToPersistenceEntityWithCorrectName() {
        // Arrange
        Category category = new Category(1L, CATEGORY_NAME);

        // Act
        CategoryEntity entity = category.toPersistence();

        // Assert
        assertThat(entity.getName()).isEqualTo(category.getName());
    }

    @Test
    void shouldBeEqualIfNamesAreSame() {
        // Arrange
        Category category1 = new Category(1L, CATEGORY_NAME);
        Category category2 = new Category(2L, CATEGORY_NAME);

        // Assert
        assertThat(category1).isEqualTo(category2);
    }

    @Test
    void shouldHaveSameHashCodeIfNamesAreSame() {
        // Arrange
        Category category1 = new Category(1L, CATEGORY_NAME);
        Category category2 = new Category(2L, CATEGORY_NAME);

        // Assert
        assertThat(category1.hashCode()).hasSameHashCodeAs(category2.hashCode());
    }

    @Test
    void shouldNotBeEqualIfNamesAreDifferent() {
        // Arrange
        Category category1 = new Category(1L, CATEGORY_NAME);
        Category category2 = new Category(2L, "Snacks");

        // Assert
        assertThat(category1).isNotEqualTo(category2);
    }
}
