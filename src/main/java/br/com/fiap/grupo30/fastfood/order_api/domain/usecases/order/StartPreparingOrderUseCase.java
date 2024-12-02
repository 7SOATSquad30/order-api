package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.CantChangeOrderStatusPreparingOtherThanSubmittedException;

public class StartPreparingOrderUseCase {

    public OrderDTO execute(OrderGateway orderGateway, Long orderId) {
        Order order = orderGateway.findById(orderId);

        if (order.getStatus() != OrderStatus.SUBMITTED) {
            throw new CantChangeOrderStatusPreparingOtherThanSubmittedException();
        }

        order.setStatus(OrderStatus.PREPARING);
        return orderGateway.save(order).toDTO();
    }
}
