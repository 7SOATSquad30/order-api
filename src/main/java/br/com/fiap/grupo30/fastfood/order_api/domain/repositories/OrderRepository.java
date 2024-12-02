package br.com.fiap.grupo30.fastfood.order_api.domain.repositories;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import java.util.List;

public interface OrderRepository {

    List<Order> findOrdersWithSpecificStatuses();

    List<Order> findOrdersByStatus(OrderStatus status);

    Order findById(Long orderId);

    Order findByIdForUpdate(Long orderId);

    Order save(Order order);
}
