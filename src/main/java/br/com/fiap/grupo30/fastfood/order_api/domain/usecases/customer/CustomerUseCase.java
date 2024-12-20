package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerUseCase {

    @Value("${external.services.customer-api.endpoint}")
    private String customerApiEndpoint;

    public Customer findCustomerByCpf(String cpf) {
        String uri = customerApiEndpoint + "/customers?cpf=" + cpf;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerDTO> response = restTemplate.getForEntity(uri, CustomerDTO.class);
        CustomerDTO result = response.getBody();
        return Customer.create(
                result.getId(), result.getName(), result.getCpf(), result.getEmail());
    }

    public Customer save(Customer customer) {
        String uri = customerApiEndpoint + "/customers";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Customer> response =
                restTemplate.postForEntity(uri, customer, Customer.class);
        return response.getBody();
    }
}
