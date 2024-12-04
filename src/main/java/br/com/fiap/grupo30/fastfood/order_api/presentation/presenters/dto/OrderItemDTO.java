package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private Long quantity;
    private Double totalPrice;
}
