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
        if (!CPF.isValid(customerCpf)) {
            throw new InvalidCpfException(customerCpf);
        }

        Customer customer = findCustomerOrCreateAnonymous(customerUseCase, new CPF(customerCpf));
        Order newOrder = Order.createFor(customer);
        return orderGateway.save(newOrder).toDTO();
    }

    public Customer findCustomerOrCreateAnonymous(
            CustomerUseCase customerUseCase, CPF customerCpf) {
        if (customerCpf != null) {
            return customerUseCase.findCustomerByCpf(customerCpf.value());
        } else {
            Customer anonymousCustomer = customerUseCase.findCustomerByCpf(Customer.ANONYMOUS_CPF);
            if (anonymousCustomer != null) {
                return anonymousCustomer;
            } else {
                Customer newAnonymousCustomer =
                        Customer.create(
                                "Anonymous", Customer.ANONYMOUS_CPF, "anonymous@fastfood.com");
                return customerUseCase.save(newAnonymousCustomer);
            }
        }
    }
}
