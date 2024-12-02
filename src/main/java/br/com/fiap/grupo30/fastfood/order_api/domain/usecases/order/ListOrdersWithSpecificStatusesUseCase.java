package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import java.util.List;

public class ListOrdersWithSpecificStatusesUseCase {

    public List<OrderDTO> execute(OrderGateway orderGateway) {
        return orderGateway.findOrdersWithSpecificStatuses().stream().map(Order::toDTO).toList();
    }
}
