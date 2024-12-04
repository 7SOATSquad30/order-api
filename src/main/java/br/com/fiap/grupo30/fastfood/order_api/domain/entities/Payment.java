package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import br.com.fiap.grupo30.fastfood.order_api.domain.PaymentStatus;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Payment {
    private PaymentStatus status;
    private Double amount;

    public static Payment create() {
        return new Payment(PaymentStatus.NOT_SUBMITTED, 0.0);
    }

    public static Payment create(PaymentStatus status, Double amount) {
        return new Payment(status, amount);
    }

    public PaymentDTO toDTO() {
        return new PaymentDTO(status, amount);
    }
}
