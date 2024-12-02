package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;

public class GetOrderUseCase {

    public OrderDTO execute(OrderGateway orderGateway, Long orderId) {
        return orderGateway.findById(orderId).toDTO();
    }
}
