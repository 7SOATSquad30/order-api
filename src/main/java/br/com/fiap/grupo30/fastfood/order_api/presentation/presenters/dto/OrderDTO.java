package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class OrderDTO {
    @Setter(AccessLevel.NONE)
    private Long orderId;

    private OrderStatus status;
    private Collection<OrderItemDTO> items;
    private Double totalPrice;
    private Long customerId;
}
