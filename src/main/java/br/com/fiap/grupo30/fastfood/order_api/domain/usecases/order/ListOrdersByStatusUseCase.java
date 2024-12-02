package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.InvalidOrderStatusException;
import java.util.List;
import java.util.Locale;

public class ListOrdersByStatusUseCase {

    public List<OrderDTO> execute(OrderGateway orderGateway, String status) {
        OrderStatus statusFilter = null;
        if (status != null && !status.isEmpty()) {
            try {
                statusFilter = OrderStatus.valueOf(status.toUpperCase(Locale.ENGLISH));
            } catch (IllegalArgumentException e) {
                throw new InvalidOrderStatusException(status, e);
            }
        }
        return orderGateway.findOrdersByStatus(statusFilter).stream().map(Order::toDTO).toList();
    }
}
