package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.CantChangeOrderStatusDeliveredOtherThanReadyException;

public class DeliverOrderUseCase {

    public OrderDTO execute(OrderGateway orderGateway, Long orderId) {
        Order order = orderGateway.findById(orderId);

        if (order.getStatus() != OrderStatus.READY) {
            throw new CantChangeOrderStatusDeliveredOtherThanReadyException();
        }

        order.setStatus(OrderStatus.DELIVERED);
        return orderGateway.save(order).toDTO();
    }
}
