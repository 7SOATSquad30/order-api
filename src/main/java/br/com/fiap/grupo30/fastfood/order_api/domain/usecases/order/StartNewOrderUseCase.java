package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer.CustomerUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.InvalidCpfException;
import org.springframework.stereotype.Component;

@Component
public class StartNewOrderUseCase {

    public OrderDTO execute(
            OrderGateway orderGateway, CustomerUseCase customerUseCase, String customerCpf) {
        if (customerCpf != null && !CPF.isValid(customerCpf)) {
            throw new InvalidCpfException(customerCpf);
        }

        if (customerCpf != null) {
            Customer customer = findCustomer(customerUseCase, new CPF(customerCpf));
            Order newOrder = Order.createFor(customer.getId());
            return orderGateway.save(newOrder).toDTO();
        } else {
            Customer customer = CreateAnonymous(customerUseCase);
            Order newOrder = Order.createFor(customer.getId());
            return orderGateway.save(newOrder).toDTO();
        }
    }

    public Customer findCustomer(CustomerUseCase customerUseCase, CPF customerCpf) {
        return customerUseCase.findCustomerByCpf(customerCpf.value());
    }

    public Customer CreateAnonymous(CustomerUseCase customerUseCase) {

        Customer anonymousCustomer = customerUseCase.findCustomerByCpf(Customer.ANONYMOUS_CPF);
        if (anonymousCustomer != null) {
            return anonymousCustomer;
        } else {
            Customer newAnonymousCustomer =
                    Customer.create(
                            9999999999L,
                            "Anonymous",
                            Customer.ANONYMOUS_CPF,
                            "anonymous@fastfood.com");
            return customerUseCase.save(newAnonymousCustomer);
        }
    }
}
