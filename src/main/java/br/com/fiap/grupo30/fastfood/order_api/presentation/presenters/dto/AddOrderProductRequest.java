package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto;

import lombok.Data;

@Data
public class AddOrderProductRequest {
    public final Long productId;
    public final Long quantity;
}
