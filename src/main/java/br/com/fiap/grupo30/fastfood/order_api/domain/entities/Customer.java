package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import br.com.fiap.grupo30.fastfood.order_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.CustomerDTO;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Customer {
    public static final String ANONYMOUS_CPF = "970.410.008-69";

    private Long id;
    private String name;
    private CPF cpf;
    private String email;

    public static Customer create(Long id, String name, String cpf, String email) {
        return new Customer(id, name, new CPF(cpf), email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(id, customer.id);
    }

    public CustomerDTO toDTO() {
        return new CustomerDTO(id, name, cpf.value(), email);
    }
}
