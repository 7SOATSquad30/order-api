package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CustomerUseCase {

    public Customer findCustomerByCpf(String cpf) {
        String uri = "http://localhost:8080/customer_api/" + cpf;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Customer> response = restTemplate.getForEntity(uri, Customer.class);
        return response.getBody();
    }

    public Customer save(Customer customer) {
        String uri = "http://localhost:8080/customer_api/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Customer> response =
                restTemplate.postForEntity(uri, customer, Customer.class);
        return response.getBody();
    }
}
