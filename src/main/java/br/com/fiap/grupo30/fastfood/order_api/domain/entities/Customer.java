package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import br.com.fiap.grupo30.fastfood.order_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.CustomerDTO;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private CPF cpf;
    private String email;

    public static Customer create(String name, String cpf, String email) {
        return new Customer(null, name, new CPF(cpf), email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(cpf.value(), customer.cpf.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf.value());
    }

    public CustomerDTO toDTO() {
        return new CustomerDTO(name, cpf.value(), email);
    }

    public CustomerEntity toPersistence() {
        return new CustomerEntity(id, name, cpf.value(), email);
    }
}
