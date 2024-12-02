package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto;

import br.com.fiap.grupo30.fastfood.order_api.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentDTO {
    private PaymentStatus status;
    private Double amount;
}
