package br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class CustomerEntityTest {

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final String DEFAULT_CUSTOMER_CPF = "29375235017";
    private static final String DEFAULT_CUSTOMER_NAME = "Exemple";
    private static final String DEFAULT_CUSTOMER_EMAIL = "exemple@exemple.com";

    @Test
    void prePersistShouldSetCreatedAt() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();

        // Act
        customer.prePersist();

        // Assert
        assertNotNull(customer.getCreatedAt(), "CreatedAt should be set during prePersist");
    }

    @Test
    void preUpdateShouldSetUpdatedAt() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();

        // Act
        customer.preUpdate();

        // Assert
        assertNotNull(customer.getUpdatedAt(), "UpdatedAt should be set during preUpdate");
    }

    @Test
    void preRemoveShouldSetDeletedAt() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();

        // Act
        customer.preRemove();

        // Assert
        assertNotNull(customer.getDeletedAt(), "DeletedAt should be set during preRemove");
    }

    @Test
    void constructorShouldCreateProductEntityWithCorrectId() {

        // Act
        CustomerEntity entity =
                new CustomerEntity(
                        DEFAULT_CUSTOMER_ID,
                        DEFAULT_CUSTOMER_NAME,
                        DEFAULT_CUSTOMER_CPF,
                        DEFAULT_CUSTOMER_EMAIL);

        // Assert
        assertThat(entity.getId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    void constructorShouldCreateProductEntityWithCorrectName() {

        // Act
        CustomerEntity entity =
                new CustomerEntity(
                        DEFAULT_CUSTOMER_ID,
                        DEFAULT_CUSTOMER_NAME,
                        DEFAULT_CUSTOMER_CPF,
                        DEFAULT_CUSTOMER_EMAIL);

        // Assert
        assertThat(entity.getName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
    }

    @Test
    void toDomainEntityShouldReturnProduct() {
        // Arrange
        // Act
        CustomerEntity entity =
                new CustomerEntity(
                        DEFAULT_CUSTOMER_ID,
                        DEFAULT_CUSTOMER_NAME,
                        DEFAULT_CUSTOMER_CPF,
                        DEFAULT_CUSTOMER_EMAIL);

        // Act
        var domainCustomer = entity.toDomainEntity();

        // Assert
        assertThat(domainCustomer.getName()).isEqualTo(entity.getName());
    }
}
