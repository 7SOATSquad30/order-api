package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerUseCase {

    private static final String PATH_VARIABLE_ID = "http://localhost:8080/customer_api/";

    public Customer findCustomerByCpf(String cpf) {
        return new RestTemplate().getForObject(PATH_VARIABLE_ID + cpf, Customer.class);
    }

    public Customer save(Customer customer) {
        return new RestTemplate().postForObject(PATH_VARIABLE_ID, customer, Customer.class);
    }
}
