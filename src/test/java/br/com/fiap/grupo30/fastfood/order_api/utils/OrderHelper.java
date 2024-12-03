package br.com.fiap.grupo30.fastfood.order_api.utils;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.OrderItem;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.PaymentDTO;

public class OrderHelper {
    private static final Long DEFAULT_ORDERITEM_QUANTITY = 1L;

    public static OrderDTO createDefaultOrderDTOWithId(
            Long id, Customer customer, Product product) {
        return createDefaultOrderWithId(id, customer, product).toDTO();
    }

    public static Order createDefaultOrderWithId(Long id, Customer customer, Product product) {
        return createOrder(id, customer, product);
    }

    public static Order createOrder(Long id, Customer customer, Product product) {
        Order order = Order.createFor(customer);
        order.addProduct(product, DEFAULT_ORDERITEM_QUANTITY);
        order.setId(id);
        return order;
    }

    public static OrderItem createOrderItem(Product product) {
        OrderItem orderItem = new OrderItem(product, DEFAULT_ORDERITEM_QUANTITY);
        return orderItem;
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
