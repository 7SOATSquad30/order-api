package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.grupo30.fastfood.order_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.order_api.utils.CustomerHelper;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void shouldCreateCustomerWithNullId() {
        // Act
        Customer customer = CustomerHelper.createDefaultCustomer();

        // Assert
        assertThat(customer.getId()).isNull();
    }

    @Test
    void shouldCreateCustomerWithName() {
        // Act
        Customer customer = CustomerHelper.createDefaultCustomer();

        // Assert
        assertThat(customer.getName()).isEqualTo("Exemple");
    }

    @Test
    void shouldCreateCustomerWithCPF() {
        // Act
        Customer customer = CustomerHelper.createDefaultCustomer();

        // Assert
        assertThat(customer.getCpf()).isEqualTo(new CPF("29375235017"));
    }

    @Test
    void shouldCreateCustomerWithEmail() {
        // Act
        Customer customer = CustomerHelper.createDefaultCustomer();

        // Assert
        assertThat(customer.getEmail()).isEqualTo("exemple@exemple.com");
    }

    @Test
    void shouldConvertToDTOWithName() {
        // Arrange
        Customer Customer = CustomerHelper.createDefaultCustomerWithId(1L);

        // Act
        CustomerDTO CustomerDTO = Customer.toDTO();

        // Assert
        assertThat(CustomerDTO.getName()).isEqualTo("Exemple");
    }

    @Test
    void shouldNotCompareEqualityWhenIdsDiffer() {
        // Arrange
        Customer Customer1 = CustomerHelper.createDefaultCustomerWithId(1L);
        Customer Customer2 =
                new Customer(2L, "Maria", new CPF("47307688093"), "exemple2@exemple.com");

        // Act & Assert
        assertThat(Customer1).isNotEqualTo(Customer2);
    }

    @Test
    void shouldHaveSameHashCodeForSameId() {
        // Arrange
        Customer Customer1 = CustomerHelper.createDefaultCustomerWithId(1L);
        Customer Customer2 = CustomerHelper.createDefaultCustomerWithId(1L);

        // Act & Assert
        assertThat(Customer1.hashCode()).hasSameHashCodeAs(Customer2.hashCode());
    }

    @Test
    void shouldHaveDifferentHashCodeForDifferentIds() {
        // Arrange
        Customer Customer1 = CustomerHelper.createDefaultCustomerWithId(1L);
        Customer Customer2 = CustomerHelper.createDefaultCustomerWithId(2L);

        // Act & Assert
        assertThat(Customer1.getId()).isNotEqualTo(Customer2.getId());
    }
}
