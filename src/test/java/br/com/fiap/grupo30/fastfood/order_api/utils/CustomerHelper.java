package br.com.fiap.grupo30.fastfood.order_api.utils;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.order_api.domain.valueobjects.CPF;

public class CustomerHelper {
    private static final String DEFAULT_CUSTOMER_CPF = "29375235017";

    public static Customer valid() {
        Long customerId = (long) (Math.random() * 1000000000);
        return new Customer(
                customerId, "João", new CPF(DEFAULT_CUSTOMER_CPF), "example@example.com");
    }
}
