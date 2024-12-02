package br.com.fiap.grupo30.fastfood.order_api.utils;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.OrderItem;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer.CustomerUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.product.ProductUseCase;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.PaymentDTO;

public class OrderHelper {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long DEFAULT_ORDERITEM_QUANTITY = 1L;
    private static final String DEFAULT_CUSTOMER_CPF = "29375235017";

    public static OrderDTO createDefaultOrderDTOWithId(
            Long id, CustomerUseCase customerUseCase, ProductUseCase productUseCase) {
        return createDefaultOrderWithId(id, customerUseCase, productUseCase).toDTO();
    }

    public static Order createDefaultOrderWithId(
            Long id, CustomerUseCase customerUseCase, ProductUseCase productUseCase) {
        return createOrder(
                id, getDefaultCustomer(customerUseCase, DEFAULT_CUSTOMER_CPF), productUseCase);
    }

    public static Order createOrder(Long id, Customer customer, ProductUseCase productUseCase) {
        Order order = Order.createFor(customer);
        order.addProduct(
                getDefaultProduct(productUseCase, DEFAULT_PRODUCT_ID), DEFAULT_ORDERITEM_QUANTITY);
        order.setId(id);
        return order;
    }

    public static OrderItem createOrderItem(ProductUseCase productUseCase) {
        OrderItem orderItem =
                new OrderItem(
                        getDefaultProduct(productUseCase, DEFAULT_PRODUCT_ID),
                        DEFAULT_ORDERITEM_QUANTITY);
        return orderItem;
    }

    public static Product getDefaultProduct(ProductUseCase productUseCase, long productId) {
        return productUseCase.findProductById(productId);
    }

    public static Customer getDefaultCustomer(CustomerUseCase customerUseCase, String cpf) {
        return customerUseCase.findCustomerByCpf(cpf);
    }

    public static OrderDTO createUpdatedOrderDTO(
            Long id,
            OrderStatus status,
            OrderItemDTO[] items,
            Double totalPrice,
            CustomerDTO customer,
            PaymentDTO payment) {
        return new OrderDTO(id, status, items, totalPrice, customer, payment);
    }
}
