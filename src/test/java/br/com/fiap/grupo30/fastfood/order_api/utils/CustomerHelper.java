package br.com.fiap.grupo30.fastfood.order_api.utils;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.order_api.domain.valueobjects.CPF;

public class CustomerHelper {
    private static final String DEFAULT_CUSTOMER_CPF = "29375235017";
    private static final String DEFAULT_CUSTOMER_NAME = "Exemple";
    private static final String DEFAULT_CUSTOMER_EMAIL = "exemple@exemple.com";

    public static Customer valid() {
        Long customerId = (long) (Math.random() * 1000000000);
        return new Customer(
                customerId, "João", new CPF(DEFAULT_CUSTOMER_CPF), "example@example.com");
    }

    public static Customer createDefaultCustomer() {
        return createCustomer(
                null, DEFAULT_CUSTOMER_NAME, DEFAULT_CUSTOMER_CPF, DEFAULT_CUSTOMER_EMAIL);
    }

    public static Customer createDefaultCustomerWithId(Long id) {
        return createCustomer(
                id, DEFAULT_CUSTOMER_NAME, DEFAULT_CUSTOMER_CPF, DEFAULT_CUSTOMER_EMAIL);
    }

    public static Customer createCustomer(Long id, String name, String cpf, String email) {
        Customer customer = Customer.create(name, cpf, email);
        customer.setId(id);
        return customer;
    }
}
