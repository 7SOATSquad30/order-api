package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import br.com.fiap.grupo30.fastfood.order_api.domain.PaymentStatus;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.PaymentEntity;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.PaymentDTO;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Payment {
    private Long id;
    private PaymentStatus status;
    private Double amount;

    public static Payment create() {
        return new Payment(null, PaymentStatus.NOT_SUBMITTED, 0.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public PaymentDTO toDTO() {
        return new PaymentDTO(status, amount);
    }

    public PaymentEntity toPersistence() {
        return new PaymentEntity(id, status, amount);
    }
}
