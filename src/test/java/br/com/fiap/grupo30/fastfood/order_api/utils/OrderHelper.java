package br.com.fiap.grupo30.fastfood.order_api.utils;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.OrderItem;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderItemDTO;
import java.util.Collection;
import java.util.LinkedList;

public class OrderHelper {
    private static final Long DEFAULT_ORDERITEM_QUANTITY = 1L;
    private static final Long DEFAULT_ORDERID = 1L;
    private static final Long DEFAULT_PRODUCTID = 1L;
    private static final Double DEFAULT_PRODUCT_PRICE = 10.50;

    public static OrderDTO createOrderDTO(
            Long id,
            OrderStatus status,
            Long customerId,
            Collection<OrderItem> items,
            Double totalPrice) {
        return createOrderWithId(id, status, customerId, items, totalPrice).toDTO();
    }

    public static Order createOrderWithId(
            Long id,
            OrderStatus status,
            Long customerId,
            Collection<OrderItem> items,
            Double totalPrice) {
        return createOrderNew(id, status, customerId, items, totalPrice);
    }

    public static Order createOrderNew(
            Long id,
            OrderStatus status,
            Long customerId,
            Collection<OrderItem> items,
            Double totalPrice) {
        Order product = new Order(null, status, customerId, items, totalPrice);
        product.setId(id);
        return product;
    }

    public static OrderDTO createDefaultOrderDTOWithId(Long id, Long customerId, Long productId) {
        return createDefaultOrderWithId(id, customerId, productId).toDTO();
    }

    public static Order createDefaultOrderWithId(Long id, Long customerId, Long productId) {
        return createOrder(id, customerId, productId);
    }

    public static Order createDefaultOrder() {
        return createOrder(DEFAULT_ORDERID, CustomerHelper.valid().getId(), DEFAULT_PRODUCTID);
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

    public static Order createOrder(Long id, Long customerId, Long productId) {
        Order order = Order.createFor(customerId);
        order.addProduct(productId, DEFAULT_ORDERITEM_QUANTITY, DEFAULT_PRODUCT_PRICE);
        order.setId(id);
        return order;
    }

    public static Order createOrderStatus(Long id, OrderStatus status) {
        Collection<OrderItem> orderItem = new LinkedList<OrderItem>();
        orderItem.add(OrderHelper.createOrderItem(DEFAULT_PRODUCTID));
        return new Order(id, status, CustomerHelper.valid().getId(), orderItem);
    }

    public static OrderItem createOrderItem(Long productId) {
        OrderItem orderItem =
                new OrderItem(productId, DEFAULT_ORDERITEM_QUANTITY, DEFAULT_PRODUCT_PRICE);
        return orderItem;
    }

    public static OrderDTO createUpdatedOrderDTO(
            Long id,
            OrderStatus status,
            Collection<OrderItemDTO> items,
            Double totalPrice,
            Long customerId) {
        return new OrderDTO(id, status, items, totalPrice, customerId);
    }
}
