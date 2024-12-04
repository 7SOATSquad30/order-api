package br.com.fiap.grupo30.fastfood.order_api.utils;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.OrderItem;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.PaymentDTO;
import java.util.Collection;
import java.util.LinkedList;

public class OrderHelper {
    private static final Long DEFAULT_ORDERITEM_QUANTITY = 1L;
    private static final Long DEFAULT_ORDERID = 1L;

    public static OrderDTO createOrderDTO(
            Long id,
            OrderStatus status,
            Customer customer,
            Payment payment,
            Collection<OrderItem> items,
            Double totalPrice) {
        return createOrderWithId(id, status, customer, payment, items, totalPrice).toDTO();
    }

    public static Order createOrderWithId(
            Long id,
            OrderStatus status,
            Customer customer,
            Payment payment,
            Collection<OrderItem> items,
            Double totalPrice) {
        return createOrderNew(id, status, customer, payment, items, totalPrice);
    }

    public static Order createOrderNew(
            Long id,
            OrderStatus status,
            Customer customer,
            Payment payment,
            Collection<OrderItem> items,
            Double totalPrice) {
        Order product = new Order(null, status, customer, payment, items, totalPrice);
        product.setId(id);
        return product;
    }

    public static OrderDTO createDefaultOrderDTOWithId(
            Long id, Customer customer, Product product) {
        return createDefaultOrderWithId(id, customer, product).toDTO();
    }

    public static Order createDefaultOrderWithId(Long id, Customer customer, Product product) {
        return createOrder(id, customer, product);
    }

    public static Order createDefaultOrder() {
        return createOrder(DEFAULT_ORDERID, CustomerHelper.valid(), ProductHelper.valid());
    }

    public static OrderDTO createDefaultOrderDTO() {
        return createDefaultOrder().toDTO();
    }

    public static Order createDefaultOrderStatus(Long id, OrderStatus status) {
        return createOrderStatus(id, status);
    }

    public static OrderDTO createDefaultOrderStatusDTO(Long id, OrderStatus status) {
        return createDefaultOrderStatus(id, status).toDTO();
    }

    public static Order createOrder(Long id, Customer customer, Product product) {
        Order order = Order.createFor(customer);
        order.addProduct(product, DEFAULT_ORDERITEM_QUANTITY);
        order.setId(id);
        return order;
    }

    public static Order createOrderStatus(Long id, OrderStatus status) {
        Collection<OrderItem> orderItem = new LinkedList<OrderItem>();
        orderItem.add(OrderHelper.createOrderItem(ProductHelper.valid()));
        return new Order(id, status, CustomerHelper.valid(), Payment.create(), orderItem);
    }

    public static OrderItem createOrderItem(Product product) {
        OrderItem orderItem = new OrderItem(product, DEFAULT_ORDERITEM_QUANTITY);
        return orderItem;
    }

    public static OrderDTO createUpdatedOrderDTO(
            Long id,
            OrderStatus status,
            Collection<OrderItemDTO> items,
            Double totalPrice,
            CustomerDTO customer,
            PaymentDTO payment) {
        return new OrderDTO(id, status, items, totalPrice, customer, payment);
    }
}
