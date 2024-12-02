package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
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
    private OrderItemDTO[] items;
    private Double totalPrice;
    private CustomerDTO customer;
    private PaymentDTO payment;
}
